package api.config;

public interface  RegisterConfig {

    /**
     * 服务端口号
     * @param port 端口号
     * @return this
     * @since 0.0.8
     */
    RegisterConfig port(final int port);

    /**
     * 启动服务
     * @return this
     * @since 0.0.8
     */
    RegisterConfig start();

}
