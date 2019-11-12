package com.yongjun.loanrouting.config;

import com.yongjun.loanrouting.pojo.Project;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author jianchun.chen.
 * @Date 2019-08-20 13:16.
 * @Description: 项目名字和规则的配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "config-project")
public class ProjectsConfig {
    private List<Project> projects;
}
