package com.yongjun.loanrouting.pojo.dto.skypay;

import com.yongjun.loanrouting.enums.SkypayEnum;
import lombok.Data;

/**
 * @Author: MaYL
 * @Description:
 * @Date: 2018/9/17 13:42
 */
@Data
public class SkyPayBaseResDTO {
    private String responseTime;
    private String responseCode;
    private String responseDescription;

    public void setResInfo(SkypayEnum skypayEnum){
        if(skypayEnum == null){
            return;
        }
        this.responseCode = skypayEnum.getCode();
        this.responseDescription = skypayEnum.getMsg();
    }
}
