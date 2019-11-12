package com.yongjun.loanrouting.enums;

/**
 * @Author: jingpj
 * @Date：creste in 2018/9/19
 */
public enum SkypayEnum {
    SUCCESS("1000","Success"),                                  //成功
    INVALID("-1000","Post context is not valid."),          //post内容无效 解密异常
    VALID_FAIL("-1001","Verification failed."),             //验证失败
    SERVICE_FAIL("-1002","Service function incorrect."),        //
    EXCEPTION_FAIL("-1009","Unpredictable exception occurs."),  //未知异常
    STORE_FAIL("-1010","Store data doesn't success, please inform contact windows."),//
    PARAMETER_FAIL("-1011","Missing necessary parameter"),                      //参数无效
    CONTROL_NUMBER_INVALID("-1012","Control number invalid or expired"),    //无效借款号
    BACK_FAIL("-1013","Backend exception occurs."),                     //后端异常
    PREFIX_FAIL("-1014","Control number of prefix is incorrect."),  // 前缀错误
    CONTRACT_NUMBER_FAIL("-1015","Contract number is invalid."),    // 无效合同号
    INSUFFICIENT_BALANCE("-1016","Insufficient balance."),  //余额不足
    CONTRACT_NUMBER_OVER("-1017","The contract number of status is completed.");    // 合同已完结

    private String code;
    private String msg;
    SkypayEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }
    public static SkypayEnum getEnumByStatus(String code) {
        for (SkypayEnum statusEnum : SkypayEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }
}
