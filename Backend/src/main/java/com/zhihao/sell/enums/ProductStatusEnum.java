package com.zhihao.sell.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum implements CodeEnum {
    UP(0, "On Sale"),
    DOWN(1, "Off Sale")
    ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
