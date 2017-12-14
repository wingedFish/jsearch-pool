package github.wingedfish.pool.jsearch.domain;

/**
 * Created by lixiuhai on 2017/12/11.
 */
public class HostAndPort {

    private String host ;
    private int port ;

    public HostAndPort(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
