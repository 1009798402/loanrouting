package com.yongjun.loanrouting.pojo.dto.skypay;

import lombok.Data;

import java.util.Date;

/**
 * @Author: MaYL
 * @Description:
 * @Date: 2018/9/17 14:09
 */
@Data
public class PayoutQueuePayoutDTO {

    private String userName;
    private String authentication;
    private String controlNumber;
    private Integer payType;
    private Date dealTime;

}
