package rpc.common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CalculateResponse implements Serializable {

    private static final long serialVersionUID = -3726677679497017305L;

    /**
     * 操作结果是否成功
     */
    private Boolean success;

    /**
     * 相加结果
     */
    private Long sum;

    public CalculateResponse(Boolean success, Long sum){
        this.success = success;

        this.sum = sum;
    }
}
