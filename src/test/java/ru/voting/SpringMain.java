package ru.voting;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.voting.repository.mock.InMemoryUserRepository;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

        InMemoryUserRepository inMemoryUserRepository = appCtx.getBean(InMemoryUserRepository.class);
        inMemoryUserRepository.init();
        inMemoryUserRepository.getAll().forEach(System.out::println);
        appCtx.close();
    }
}
