package rpc.common.model;

import lombok.Data;

@Data
public class CalculateRequest {

    /**
     * 加数one
     */
    private Long one;

    /**
     * 加数two
     */
    private Long two;

    public CalculateRequest(Long one, Long two){
        this.one = one;
        this.two = two;
    }
}
