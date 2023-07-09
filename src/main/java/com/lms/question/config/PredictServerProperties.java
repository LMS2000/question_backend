package com.lms.question.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "predict-server")
public class PredictServerProperties {

    private String predictUrl;
}
