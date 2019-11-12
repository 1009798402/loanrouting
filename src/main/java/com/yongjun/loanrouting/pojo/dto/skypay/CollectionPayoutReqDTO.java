package com.yongjun.loanrouting.pojo.dto.skypay;

import lombok.Data;

import java.util.Date;

/**
 * @Author: MaYL
 * @Description:
 * @Date: 2018/9/17 14:30
 */
@Data
public class CollectionPayoutReqDTO {

    private String userName;
    private String action;
    private String authentication;
    private String contractNumber;
    private Double amount;
    private String phone;
    private String receiptNumber;
    //yyyy-MM-dd HH:mm:ss
    private Date collectedTime;
    private Integer payChannel;

}
