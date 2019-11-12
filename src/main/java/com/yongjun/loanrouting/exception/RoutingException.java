package com.yongjun.loanrouting.exception;


import com.yongjun.loanrouting.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author jianchun.chen.
 * @Date 2019-08-19 17:09.
 * @Description: 自定义抛出的异常
 */
@Slf4j
public class RoutingException extends RuntimeException{

    private Integer code;

    public RoutingException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public RoutingException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
