package org.example;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component

public class PaymentService {

    public void Pay(){
        System.out.println("Payment done");
    }
}
