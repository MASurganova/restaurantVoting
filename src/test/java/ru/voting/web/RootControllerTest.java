package ru.voting.web;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.voting.TestData.MY;
import static ru.voting.TestData.USER;
import static ru.voting.TestData.USER_ID;
import static ru.voting.model.AbstractBaseEntity.START_SEQ;

public class RootControllerTest extends AbstractControllerTest {

    @Test
    public void testUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(USER_ID)),
                                hasProperty("name", is(USER.getName()))
                        )
                )));
    }

    @Test
    public void testVoting() throws Exception {
        mockMvc.perform(get("/voting"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("voting"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/voting.jsp"))
                .andExpect(model().attribute("restaurants", hasSize(1)))
                .andExpect(model().attribute("restaurants", hasItem(
                        allOf(
                                hasProperty("id", is(MY.getId())),
                                hasProperty("name", is(MY.getName()))
                        )
                )));
    }

    @Test
    public void testRestaurants() throws Exception {
        mockMvc.perform(get("/restaurants"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("restaurants"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/restaurants.jsp"))
                .andExpect(model().attribute("restaurants", hasSize(2)))
                .andExpect(model().attribute("restaurants", hasItem(
                        allOf(
                                hasProperty("id", is(MY.getId())),
                                hasProperty("name", is(MY.getName()))
                        )
                )));
    }

}