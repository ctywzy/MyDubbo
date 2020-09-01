package rpc.common.model;

import lombok.Data;

@Data
public class CalculateResponse {

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
