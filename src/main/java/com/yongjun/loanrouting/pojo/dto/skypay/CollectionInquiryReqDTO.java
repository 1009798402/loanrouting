package com.yongjun.loanrouting.pojo.dto.skypay;

import lombok.Data;

/**
 * @Author: MaYL
 * @Description:
 * @Date: 2018/9/17 14:27
 */
@Data
public class CollectionInquiryReqDTO {

    private String userName;
    private String action;
    private String authentication;
    private String contractNumber;
    private Double amount;
    private String phone;
}
