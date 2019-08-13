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

    public ApplicationProperties() {
    }

    public Paypal getPaypal() {
        return paypal;
    }

    public void setPaypal(Paypal paypal) {
        this.paypal = paypal;
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
}
