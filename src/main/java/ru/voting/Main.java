package ru.voting;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.voting.repository.MockRepository.InMemoryRestaurantRepository;
import ru.voting.repository.MockRepository.InMemoryUserRepository;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

        InMemoryUserRepository inMemoryUserRepository = appCtx.getBean(InMemoryUserRepository.class);
        inMemoryUserRepository.getAll();
        appCtx.close();

        InMemoryRestaurantRepository restaurantRepository = appCtx.getBean(InMemoryRestaurantRepository.class);
        restaurantRepository.getAll().forEach(System.out::println);
    }
}
