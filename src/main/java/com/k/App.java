package com.k;

import javax.security.auth.login.LoginException;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class App {
    public static void main(String[] args) throws LoginException {
        ConfigurableApplicationContext applicationContext = new AnnotationConfigApplicationContext(App.class);
        applicationContext.getBean(Overseer.class).boot();
    }
}
