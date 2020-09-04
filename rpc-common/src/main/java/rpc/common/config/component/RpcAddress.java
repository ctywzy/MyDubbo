package rpc.common.config.component;

/**
 * @Description 地址信息
 * @Author wangzy
 * @Date 2020/9/4 9:38 上午
 **/
public class RpcAddress {

    /**
     * address 信息
     */
    private String address;

    /**
     * 端口号
     */
    private int port;

    /**
     * 权重
     */
    private int weight;

    public RpcAddress(String address, int port, int weight){
        this.address = address;
        this.port = port;
        this.weight = weight;
    }

    public String address(){
        return address;
    }

    public RpcAddress address(String ip){
        this.address = ip;
        return this;
    }

    public int port() {
        return port;
    }

    public RpcAddress port(int port) {
        this.port = port;
        return this;
    }

    public int weight() {
        return weight;
    }

    public RpcAddress weight(int weight) {
        this.weight = weight;
        return this;
    }

}
