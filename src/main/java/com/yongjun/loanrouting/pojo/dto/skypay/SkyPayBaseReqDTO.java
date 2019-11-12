package com.yongjun.loanrouting.pojo.dto.skypay;

import lombok.Data;

/**
 * @Author jianchun.chen.
 * @Date 2019-08-19 17:44.
 * @Description:
 */
@Data
public class SkyPayBaseReqDTO {
    private String userName;
    private String action;
    private String authentication;
    private String controlNumber;
}