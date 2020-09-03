package rpc.common.model;

import lombok.Data;

import java.io.Serializable;

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
