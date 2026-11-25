package org.example;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// Given below is lifecycle after of beans after Dependency Injection are Resolved
@Component
public class CardService {
// public class CardService implements InitializingBean,DisposableBean, BeanNameAware, ApplicationContextAware {

    Map<Integer,String> mp ;

    public CardService(){
        mp = new HashMap<>();
        System.out.println("Card Service Constructor called");
    }

    public void addCard(){
        System.out.println("added card");
    }

//    1. Aware Callbacks are been called before
//    @Override
//    public void setBeanName(String name){
//        System.out.println("callback called by IoC : "+name);
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        System.out.println("self Aware method for getting application context : "+ applicationContext);
//    }

//    1. Initialization Callbacks

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("Initialization callback");
//        System.out.println("Bean is ready");
//        mp.put(1,"Aditya");
//        mp.put(2,"Rohit");
//    }

//    Init Method approch for Intilization callback
//    public void start() throws Exception {
//        System.out.println("Bean is ready by init method approach");
//        mp.put(1,"Aditya");
//        mp.put(2,"Rohit");
//    }

//    PostConstructor [need JakartaNotation dependency]
//    @PostConstruct
//    public void start() throws Exception {
//        System.out.println("Initialization callback");
//        System.out.println("Bean is ready");
//        mp.put(1,"Aditya");
//        mp.put(2,"Rohit");
//    }

//    2. Bussiness logics

    public void getValue(int Key){
        System.out.println(mp.get(Key));
    }


//    3. Destroyable Callbacks

//    this is destroy Callback get called before beans get destoryed
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("Destroy Callback by DisposableBean interface");
//    }

//    destroy method approach for destroy callback
//      public void stop(){
//          System.out.println("Destroy Callback by destroyMethod approach");
//      }

    @PreDestroy
    public void stop(){
        System.out.println("Destroy Callback by PreDestroy Annotation");
    }
}
