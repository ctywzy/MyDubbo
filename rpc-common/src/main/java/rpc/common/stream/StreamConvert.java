package rpc.common.stream;

import com.google.common.base.Throwables;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import rpc.common.domain.RpcResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @Description BufByte和对象互相转换工具类
 * @Author wangzy
 * @Date 2020/9/23 9:56 上午
 **/
@Slf4j
public class StreamConvert {

    private static ObjectInputStream ois;

    private static ObjectOutputStream oos;

    private static ByteArrayInputStream bis;

    private static ByteArrayOutputStream bos;

    /**
     * @description 将字节数组转换为对象
     * 从字节流中读出对象
     * @param <T>
     * @param msg
     * @return
     */
    public static <T> T bytesToObject(Object msg){

        // 字节缓冲区
        ByteBuf byteBuf = (ByteBuf)msg;

        //设置数组大小
        byte[] bytes = new byte[byteBuf.readableBytes()];
        // 将byteBuf中的数据读到bytes中
        byteBuf.readBytes(bytes);

        bis = new ByteArrayInputStream(bytes);
        try{
            ois = new ObjectInputStream(bis);
            return (T) ois.readObject();
        }catch (Exception e){
            log.info("StreamConvert.bytesToObject.error: {}", Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * @description 将对象转换为字节数组
     * 将对象转换为字节流
     * @param obj
     * @return
     */
    public static ByteBuf objectToBytes(Object obj){

        bos = new ByteArrayOutputStream();
        try{
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            byte[] bytes = bos.toByteArray();
            return Unpooled.copiedBuffer(bytes);
        }catch (Exception e){
            log.info("StreamConvert.objectToBytes.error: {}", Throwables.getStackTraceAsString(e));
            return null;
        }
    }
}
