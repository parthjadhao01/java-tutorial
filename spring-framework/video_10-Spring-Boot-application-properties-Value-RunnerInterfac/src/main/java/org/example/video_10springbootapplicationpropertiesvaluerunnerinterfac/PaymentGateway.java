package org.example.video_10springbootapplicationpropertiesvaluerunnerinterfac;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentGateway {

//    @Value("${paymentgateway.retrycount:5}")
//    private int RetryCount;
//    @Value("${paymentgateway.type:Razorpay}")
//    private String Type;


//    public PaymentGateway(
//            @Value("${paymentgateway.retrycount}") int retryCount,
//            @Value("${paymentgateway.type}") String type) {
//        RetryCount = retryCount;
//        Type = type;
//    }

    private PaymentProperties paymentProperties;

    public PaymentGateway(PaymentProperties paymentProperties) {
        this.paymentProperties = paymentProperties;
    }

    public String getType() {
        return paymentProperties.getTypes();
    }

//    public void setType(String type) {
//        Type = type;
//    }


    public int getRetryCount() {
        return paymentProperties.getRetryCount();
    }

//    public void setRetryCount(int retryCount) {
//        RetryCount = retryCount;
//    }

    public void print(){
        System.out.println(getType());
        System.out.println(getRetryCount());
        System.out.println(paymentProperties.isEnable());
    }
}
