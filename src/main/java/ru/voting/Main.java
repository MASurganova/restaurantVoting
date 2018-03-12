package ru.voting;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.voting.repository.UserRepository;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

        UserRepository userRepository = (UserRepository) appCtx.getBean("userRepository");
//        UserRepository userRepository = appCtx.getBean(UserRepository.class);
        userRepository.getAll();
        appCtx.close();
    }
}
