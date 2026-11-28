package org.example.video_10springbootapplicationpropertiesvaluerunnerinterfac;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("payment-property")
public class PaymentProperties {
    private String types;
    private int retryCount;
    private boolean isEnable;

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }


    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
}
