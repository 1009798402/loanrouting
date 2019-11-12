package com.yongjun.loanrouting.controller;

import com.alibaba.fastjson.JSON;
import com.yongjun.loanrouting.config.ProjectsConfig;
import com.yongjun.loanrouting.constants.ProjectConstants;
import com.yongjun.loanrouting.enums.SkypayEnum;
import com.yongjun.loanrouting.pojo.Project;
import com.yongjun.loanrouting.pojo.dto.skypay.*;
import com.yongjun.loanrouting.utils.AESUtil2;
import com.yongjun.loanrouting.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author jianchun.chen.
 * @Date 2019-08-19 17:20.
 * @Description: skyPay的路由的controller
 */
@Slf4j
@RestController
@RequestMapping("/skypay")
public class SkyPayRoutingController {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private ProjectsConfig projectsConfig;

    @Value("${skypay.encryptKey}")
    private String encryptKey;

    @PostMapping("/payoutInquiry")
    public void payoutInquiryRouting(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //解密
        String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        PayoutInquiryReqDTO dto = parseReq(body, PayoutInquiryReqDTO.class);
        String controlNumber = dto.getControlNumber();
        //根据单号匹配
        String projectUrlPrefix = matchingProductCallback(response, controlNumber, projectsConfig);

        //==null已经做出了响应
        if (projectUrlPrefix == null) {
            return;
        } else if (projectUrlPrefix.equals(ProjectConstants.NO_MATCH_SUCCESSFUL)) {
            //没有匹配上就走全部循环
            List<Project> projects = projectsConfig.getProjects();
            for (Project project : projects) {
                PayoutInquiryResDTO resDTO = routing(body, project.getPrefix() + ProjectConstants.INTERFACE_PAYOUTINQUIRY, PayoutInquiryResDTO.class);
                //如果出现成功  或者 重复处理  直接响应
                if (resDTO.getResponseCode().equals(SkypayEnum.SUCCESS.getCode()) ||
                        resDTO.getResponseCode().equals(SkypayEnum.CONTRACT_NUMBER_OVER.getCode())) {
                    writerRes(response, resDTO);
                    return;
                }
            }
            //如果全部没匹配上 响应无效借款单号
            writerInvalidNumber(response) ;
            return;
        }

        //匹配上
        PayoutInquiryResDTO resDTO = routing(body, projectUrlPrefix + ProjectConstants.INTERFACE_PAYOUTINQUIRY, PayoutInquiryResDTO.class);
        writerRes(response, resDTO);
    }

    @PostMapping("/payout")
    public void payoutRouting(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        PayoutPayoutReqDTO dto = parseReq(body, PayoutPayoutReqDTO.class);
        String controlNumber = dto.getControlNumber();
        //根据单号匹配
        String projectUrlPrefix = matchingProductCallback(response, controlNumber, projectsConfig);

        //==null已经做出了响应
        if (projectUrlPrefix == null) {
            return;
        } else if (projectUrlPrefix.equals(ProjectConstants.NO_MATCH_SUCCESSFUL)) {
            //没有匹配上就走全部循环
            List<Project> projects = projectsConfig.getProjects();
            for (Project project : projects) {
                SkyPayBaseResDTO resDTO = routing(body, project.getPrefix() + ProjectConstants.INTERFACE_PAYOUT, SkyPayBaseResDTO.class);
                //如果出现成功  直接响应
                if (resDTO.getResponseCode().equals(SkypayEnum.SUCCESS.getCode())) {
                    writerRes(response, resDTO);
                    return;
                }
            }
            //如果全部没匹配上 响应无效借款单号
            writerInvalidNumber(response) ;
            return;
        }

        //匹配上
        SkyPayBaseResDTO resDTO = routing(body, projectUrlPrefix + ProjectConstants.INTERFACE_PAYOUT, SkyPayBaseResDTO.class);
        writerRes(response, resDTO);
    }

    @PostMapping("/collectionInquiry")
    public void collectionInquiryRouting(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        CollectionInquiryReqDTO dto = parseReq(body, CollectionInquiryReqDTO.class);
        String controlNumber = dto.getContractNumber();

        //根据单号匹配
        String projectUrlPrefix = matchingProductCallback(response, controlNumber, projectsConfig);

        //==null已经做出了响应
        if (projectUrlPrefix == null) {
            return;
        } else if (projectUrlPrefix.equals(ProjectConstants.NO_MATCH_SUCCESSFUL)) {
            //没有匹配上就走全部循环
            List<Project> projects = projectsConfig.getProjects();
            for (Project project : projects) {
                SkyPayBaseResDTO resDTO = routing(body, project.getPrefix() + ProjectConstants.INTERFACE_COLLECTIONINQUIRY, SkyPayBaseResDTO.class);
                //如果出现成功  直接响应
                if (resDTO.getResponseCode().equals(SkypayEnum.SUCCESS.getCode())) {
                    writerRes(response, resDTO);
                    return;
                }
            }
            //如果全部没匹配上 响应无效借款单号
            writerInvalidNumber(response) ;
            return;
        }
        //匹配上
        SkyPayBaseResDTO resDTO = routing(body, projectUrlPrefix + ProjectConstants.INTERFACE_COLLECTIONINQUIRY, SkyPayBaseResDTO.class);
        writerRes(response, resDTO);
    }

    @PostMapping("/collectionPayout")
    public void collectionPayoutRouting(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        CollectionPayoutReqDTO dto = parseReq(body, CollectionPayoutReqDTO.class);
        String controlNumber = dto.getContractNumber();

        //根据单号匹配
        String projectUrlPrefix = matchingProductCallback(response, controlNumber, projectsConfig);

        //==null已经做出了响应
        if (projectUrlPrefix == null) {
            return;
        } else if (projectUrlPrefix.equals(ProjectConstants.NO_MATCH_SUCCESSFUL)) {
            //没有匹配上就走全部循环
            List<Project> projects = projectsConfig.getProjects();
            for (Project project : projects) {
                SkyPayBaseResDTO resDTO = routing(body, project.getPrefix() + ProjectConstants.INTERFACE_COLLECTIONPAYOUT, SkyPayBaseResDTO.class);
                //如果出现成功  直接响应
                if (resDTO.getResponseCode().equals(SkypayEnum.SUCCESS.getCode()) ||
                        resDTO.getResponseCode().equals(SkypayEnum.CONTRACT_NUMBER_OVER.getCode())) {
                    writerRes(response, resDTO);
                    return;
                }
            }
            //如果全部没匹配上 响应无效借款单号
            writerInvalidNumber(response) ;
            return;
        }
        //匹配上
        SkyPayBaseResDTO resDTO = routing(body, projectUrlPrefix + ProjectConstants.INTERFACE_COLLECTIONPAYOUT, SkyPayBaseResDTO.class);
        writerRes(response, resDTO);
    }

    @PostMapping("/payoutQueuePayout")
    public void payoutQueuePayoutRouting(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        PayoutQueuePayoutDTO dto = parseReq(body, PayoutQueuePayoutDTO.class);
        String controlNumber = dto.getControlNumber();

        //根据单号匹配
        String projectUrlPrefix = matchingProductCallback(response, controlNumber, projectsConfig);

        //==null已经做出了响应
        if (projectUrlPrefix == null) {
            return;
        } else if (projectUrlPrefix.equals(ProjectConstants.NO_MATCH_SUCCESSFUL)) {
            //没有匹配上就走全部循环
            List<Project> projects = projectsConfig.getProjects();
            for (Project project : projects) {
                SkyPayBaseResDTO resDTO = routing(body, project.getPrefix() + ProjectConstants.INTERFACE_PAYOUTQUEUEPAYOUT, SkyPayBaseResDTO.class);
                //如果出现成功  直接响应
                if (resDTO.getResponseCode().equals(SkypayEnum.SUCCESS.getCode()) ||
                        resDTO.getResponseCode().equals(SkypayEnum.CONTRACT_NUMBER_OVER.getCode())) {
                    writerRes(response, resDTO);
                    return;
                }
            }
            //如果全部没匹配上 响应无效借款单号
            writerInvalidNumber(response) ;
            return;
        }
        //匹配上
        SkyPayBaseResDTO resDTO = routing(body, projectUrlPrefix + ProjectConstants.INTERFACE_PAYOUTQUEUEPAYOUT, SkyPayBaseResDTO.class);
        writerRes(response, resDTO);
    }

    private <T> T routing(String body, String url, Class<T> clazz) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        ResponseEntity<String> exchange1 = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), String.class);
        return parseReq(exchange1.getBody(), clazz);
    }


    private <T> T parseReq(String body, Class<T> clazz) {
        String req = AESUtil2.decrypt(body, encryptKey);
        return JSON.parseObject(req, clazz);
    }

    /**
     * 根据单号匹配项目
     *
     * @param controlNumber   单号
     * @param projectsConfigs 项目匹配的规则
     * @return 匹配到返回该项目的域名 没有匹配上返回NO_MATCH_SUCCESSFUL null表示配置有问题
     */
    private String matchingProductCallback(HttpServletResponse response, String controlNumber, ProjectsConfig projectsConfigs) {

        log.info("单号 =  {}",controlNumber);
        SkyPayBaseResDTO resDTO = new SkyPayBaseResDTO();
        resDTO.setResponseTime(DateUtils.format(DateUtils.getCurrent(), "yyyy-MM-dd HH:mm:ss.SSS0"));
        if (StringUtils.isEmpty(controlNumber) || controlNumber.length() < 6) {
            log.error("单号长度有问题或者单号为空！");
            resDTO.setResInfo(SkypayEnum.CONTROL_NUMBER_INVALID);
            writerRes(response, resDTO);
            return null;
        }
        String numberPrefix = controlNumber.substring(0, 6);
        if (projectsConfigs == null || projectsConfigs.getProjects() == null) {
            log.error("单号长度有问题或者单号为空！");
            resDTO.setResInfo(SkypayEnum.BACK_FAIL);
            writerRes(response, resDTO);
            return null;
        }

        List<Project> projects = projectsConfigs.getProjects();
        for (Project project : projects) {
            if (numberPrefix.contains(project.getRule())) {
                return project.getPrefix();
            }
        }
        return ProjectConstants.NO_MATCH_SUCCESSFUL;
    }

    private void writerRes(HttpServletResponse response, Object obj) {
        try {
            String res = JSON.toJSONString(obj);
            //aes解密skypay数据
            res = AESUtil2.encrypt(res, encryptKey);
            IOUtils.write(res, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writerInvalidNumber(HttpServletResponse response){
        log.error("没有单号没有匹配上");
        SkyPayBaseResDTO resDTO = new SkyPayBaseResDTO();
        resDTO.setResponseTime(DateUtils.format(DateUtils.getCurrent(), "yyyy-MM-dd HH:mm:ss.SSS0"));
        resDTO.setResInfo(SkypayEnum.CONTROL_NUMBER_INVALID);
        writerRes(response, resDTO);
    }
}
