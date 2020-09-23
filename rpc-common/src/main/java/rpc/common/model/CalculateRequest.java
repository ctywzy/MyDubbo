package rpc.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 计算请求类
 * @Author wangzy
 * @Date 2020/9/23 9:54 上午
 **/
@Data
public class CalculateRequest implements Serializable {

    private static final long serialVersionUID = -8297562136615599943L;

    /**
     * 加数one
     */
    private Long one;

    /**
     * 加数two
     */
    private Long two;

    public CalculateRequest(Long one, Long two) {
        this.one = one;
        this.two = two;
    }
}
