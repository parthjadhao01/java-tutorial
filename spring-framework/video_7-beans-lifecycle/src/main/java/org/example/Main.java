package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

// 1.start IoC container
// 2.and give configuration
// 3.Read Bean Definition
// 4.Instantiate Objects
// 5.Dependecies Are Injected [4 and 5th happen simiultenously if depedencies Injection happen parrallely]
// 6.Aware Interface are called
// 7.Intialization Callbacks [Initalization Bean, initMethod,PostConstruct Annotation]
// 8.Bean is ready to use
// 9. Desitruction callbacks [DisposableBean , destroyMethod , PreDestroy]

public class Main {
    static void main() {

        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

//        OrderService orderService = context.getBean(OrderService.class);
//        orderService.placeOrder();

        CardService cardService = context.getBean(CardService.class);
        cardService.getValue(1);

        context.close();


    }
}
