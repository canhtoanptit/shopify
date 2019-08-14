package com.paypal.mng.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Paypalmng.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private Paypal paypal = new ApplicationProperties.Paypal();
    private Redis redis = new ApplicationProperties.Redis();

    public ApplicationProperties() {
    }

    public Paypal getPaypal() {
        return paypal;
    }

    public void setPaypal(Paypal paypal) {
        this.paypal = paypal;
    }

    public Redis getRedis() {
        return redis;
    }

    public void setRedis(Redis redis) {
        this.redis = redis;
    }

    public static class Paypal {
        private String host;

        public Paypal() {
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }
    }
    public static class Redis {
        private String host;

        public Redis() {
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }
    }
}
