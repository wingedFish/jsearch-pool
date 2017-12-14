package github.wingedfish.pool.jsearch;

import github.wingedfish.pool.jsearch.domain.HostAndPort;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lixiuhai on 2017/12/11.
 */
public class JsearchClient {

    private static Set<HostAndPort> nodes = new HashSet<>();

    static {
        nodes.add(new HostAndPort("192.168.182.12", 20102));
    }

    @Test
    public void testConn() {

        JsearchPoolConfig jsearchPoolConfig = new JsearchPoolConfig(1000);
        jsearchPoolConfig.setBlockWhenExhausted(false);
        JsearchPool pool = new JsearchPool(nodes, "jiesi-2.1.2", false,jsearchPoolConfig);
        for (int i = 0; i < 8; i++) {
            TransportClient client = pool.getConnection();
//            GetResponse responseGet = client.prepareGet("barkeeper_monitor_index", "barkeeper_monitor_type", "1501").execute().actionGet();
            System.out.println("-----------"+i+" ====== " +client.toString());
            pool.returnResource(client);
            System.out.println("-----------"+i+" ======释放链接： " +client.toString());
        }
    }

}
