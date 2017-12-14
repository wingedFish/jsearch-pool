package github.wingedfish.pool.jsearch;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Created by lixiuhai on 2017/12/11.
 */
public class JsearchPoolConfig extends GenericObjectPoolConfig {

    private long connTimeMillis;

    public JsearchPoolConfig(long connTimeMillis) {
        this.connTimeMillis = connTimeMillis;
    }
    public JsearchPoolConfig() {
        this.connTimeMillis = 1000;
    }
}
