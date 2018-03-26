package ru.voting.service.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.voting.TestData;
import ru.voting.model.Role;
import ru.voting.model.User;
import ru.voting.repository.mock.InMemoryUserRepository;
import ru.voting.service.UserService;
import ru.voting.util.exception.NotFoundException;

import java.util.Collection;

@ContextConfiguration({"classpath:spring/mock.xml",
                        "classpath:spring/spring-app.xml"} )
@RunWith(SpringRunner.class)
public class InMemoryUserServiceTest {

    @Autowired
    private UserService service;

    @Autowired
    private InMemoryUserRepository repository;


    @Before
    public void setUp() throws Exception {
        repository.init();
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(TestData.USER_ID);
        Collection<User> users = service.getAll();
        Assert.assertEquals(users.size(), 1);
        Assert.assertEquals(users.iterator().next(), TestData.ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(10);
    }

    @Test
    public void testGet() throws Exception {
        User user = service.get(TestData.USER_ID);
        Assert.assertEquals(user, TestData.USER);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(3);
    }

    @Test
    public void testGetByEmail() throws Exception {
        User user = service.getByEmail("user@yandex.ru");
        Assert.assertEquals(user, TestData.USER);
    }

    @Test(expected = NotFoundException.class)
    public void testGetByEmailNotFound() throws Exception {
        service.getByEmail("123@123.ru");
    }

    @Test
    public void testCreate() {
        service.create(new User(null, "Marina", "marina@mail.ru", "marina", Role.ROLE_USER));
        Assert.assertEquals(service.getAll().size(), 3);
        Assert.assertEquals(Long.valueOf(service.getByEmail("marina@mail.ru").getId()), Long.valueOf(3));
    }

    @Test
    public void testUpdate() {
        TestData.USER.setName("Marina");
        service.update(TestData.USER);
        Assert.assertEquals(service.get(TestData.USER_ID), TestData.USER);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() {
        User user = new User(5, "m", "m", "m", Role.ROLE_USER);
        service.update(user);
    }
}
