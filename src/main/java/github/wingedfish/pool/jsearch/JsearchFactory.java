package github.wingedfish.pool.jsearch;

import github.wingedfish.pool.jsearch.domain.Constants;
import github.wingedfish.pool.jsearch.domain.HostAndPort;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by lixiuhai on 2017/12/11.
 */
public class JsearchFactory implements PooledObjectFactory<TransportClient> {

    private AtomicReference<Set<HostAndPort>> nodesReference = new AtomicReference<>();

    private String clusterName;
    private boolean sniff;

    public JsearchFactory(Set<HostAndPort> nodes, String clusterName, boolean sniff) {
        nodesReference.set(nodes);
        this.clusterName = clusterName;
        this.sniff = sniff;
    }

    /**
     * Create an instance that can be served by the pool and wrap it in a
     * {@link PooledObject} to be managed by the pool.
     *
     * @return a {@code PooledObject} wrapping an instance that can be served by the pool
     * @throws Exception if there is a problem creating a new instance,
     *                   this will be propagated to the code requesting an object.
     */


    @Override
    public PooledObject<TransportClient> makeObject() throws Exception {
        Set<HostAndPort> nodes = nodesReference.get();
        TransportClient transportClient = createObject(nodes);
        return new DefaultPooledObject(transportClient);
    }

    private TransportClient createObject(Set<HostAndPort> nodes) {
        Settings settings = Settings.settingsBuilder().put(Constants.CLUSTER_NAME, clusterName)
                .put(Constants.TRANSPORT_SNIFF, sniff).build();
        TransportClient transportClient = TransportClient.builder()
                .settings(settings)
                .build();

        for (HostAndPort hostAndPort : nodes) {
            try {
                transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostAndPort.getHost()), hostAndPort.getPort()));
            } catch (UnknownHostException e) {
                throw new IllegalArgumentException("Unknown Host : " + hostAndPort.getHost());
            }
        }
        return transportClient;

    }

    /**
     * Destroys an instance no longer needed by the pool.
     * <p>
     * It is important for implementations of this method to be aware that there
     * is no guarantee about what state <code>obj</code> will be in and the
     * implementation should be prepared to handle unexpected errors.
     * <p>
     * Also, an implementation must take in to consideration that instances lost
     * to the garbage collector may never be destroyed.
     * </p>
     *
     * @param p a {@code PooledObject} wrapping the instance to be destroyed
     * @throws Exception should be avoided as it may be swallowed by
     *                   the pool implementation.
     * @see #validateObject
     * @see ObjectPool#invalidateObject
     */
    @Override
    public void destroyObject(PooledObject<TransportClient> p) throws Exception {
        p.getObject().close();
    }

    /**
     * Ensures that the instance is safe to be returned by the pool.
     *
     * @param p a {@code PooledObject} wrapping the instance to be validated
     * @return <code>false</code> if <code>obj</code> is not valid and should
     * be dropped from the pool, <code>true</code> otherwise.
     */
    @Override
    public boolean validateObject(PooledObject<TransportClient> p) {
        return false;
    }

    /**
     * Reinitialize an instance to be returned by the pool.
     *
     * @param p a {@code PooledObject} wrapping the instance to be activated
     * @throws Exception if there is a problem activating <code>obj</code>,
     *                   this exception may be swallowed by the pool.
     * @see #destroyObject
     */
    @Override
    public void activateObject(PooledObject<TransportClient> p) throws Exception {

    }

    /**
     * Uninitialize an instance to be returned to the idle object pool.
     *
     * @param p a {@code PooledObject} wrapping the instance to be passivated
     * @throws Exception if there is a problem passivating <code>obj</code>,
     *                   this exception may be swallowed by the pool.
     * @see #destroyObject
     */
    @Override
    public void passivateObject(PooledObject<TransportClient> p) throws Exception {

    }
}
