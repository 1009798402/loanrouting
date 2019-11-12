package com.yongjun.loanrouting.pojo.dto.skypay;

import lombok.Data;

import java.util.Date;

/**
 * @Author: MaYL
 * @Description:
 * @Date: 2018/9/17 14:25
 */
@Data
public class PayoutPayoutReqDTO extends SkyPayBaseReqDTO {
    //yyyy-MM-dd HH:mm:ss
    private String withdrawChannel;
    private Date payTime;
}
