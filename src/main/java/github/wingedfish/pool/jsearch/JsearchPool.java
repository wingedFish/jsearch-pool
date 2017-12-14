package github.wingedfish.pool.jsearch;

import github.wingedfish.pool.jsearch.domain.HostAndPort;
import org.elasticsearch.client.transport.TransportClient;

import java.util.Set;

/**
 * Created by lixiuhai on 2017/12/11.
 */
public class JsearchPool extends Pool<TransportClient> {
    private String clusterName ;
    private boolean sniff ;

    /**
     * Using this constructor means you have to set and initialize the internalPool yourself.
     */

    public JsearchPool(Set<HostAndPort> nodes,String clusterName,boolean sniff,JsearchPoolConfig config) {
        super(config, new JsearchFactory(nodes,clusterName,sniff));
    }

    public TransportClient getConnection(){
        return super.getResource();
    }

    public void close(TransportClient client){
        super.returnResource(client);
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public boolean isSniff() {
        return sniff;
    }

    public void setSniff(boolean sniff) {
        this.sniff = sniff;
    }
}
