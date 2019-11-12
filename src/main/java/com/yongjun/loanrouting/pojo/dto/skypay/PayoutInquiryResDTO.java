package com.yongjun.loanrouting.pojo.dto.skypay;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @Author: MaYL
 * @Description:
 * @Date: 2018/9/17 14:33
 */
@Data
public class PayoutInquiryResDTO extends SkyPayBaseResDTO {

    private String sender;
    private String controlNumber;
    private String name;
    @JSONField(format = "yyyy-MM-dd")
    private Date birthday;
    private String identificationId;
    private String idType;
    private String phone;
    private Double amount;
    private String idcardPicType;
    private String idcardPicUrl;
    private String contractNumber;
    private String location;
    private Integer withdrawChannel;
    private Integer realtime;
    private Integer status;

}
