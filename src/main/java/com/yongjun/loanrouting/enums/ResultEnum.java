package com.yongjun.loanrouting.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author jianchun.chen.
 * @Date 2019-08-19 17:09.
 * @Description: 结果的枚举
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {
    /** 1.SkyPay返回解析异常 */
    PARAM_ERROR(1, "SkyPay返回解析异常"),
    /** 2.项目配置有误 */
    PROJECT_CONFIG_ERROR(2, "项目配置有误"),
    ;
    private Integer code;
    private String msg;
}
