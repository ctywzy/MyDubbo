package rpc.common.exception;

/**
 * @Description 超时异常类型定义
 * @Author wangzy
 * @Date 2020/9/23 9:55 上午
 **/
public class RpcTimeOutException extends RuntimeException {

    private static final long serialVersionUID = -6761687433481998826L;


    public RpcTimeOutException() {
    }

    public RpcTimeOutException(String message) {
        super(message);
    }

    public RpcTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcTimeOutException(Throwable cause) {
        super(cause);
    }

    public RpcTimeOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
