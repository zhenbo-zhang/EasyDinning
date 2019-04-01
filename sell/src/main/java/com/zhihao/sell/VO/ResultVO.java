package com.zhihao.sell.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
/*
* the outer object of http request
* */
@Data
public class ResultVO<T> {

    /** wrong code. */
    private Integer code;

    /** message. */
    private String msg;

    /** detail. */
    private T data;
}
