package ru.voting.web.voting;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import ru.voting.TestUtil;
import ru.voting.web.AbstractControllerTest;
import ru.voting.web.json.JsonUtil;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.TestData.*;
import static ru.voting.TestUtil.userHttpBasic;

public class VotingRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VotingRestController.REST_URL + '/';

    @Test
    public void getEnabledRestaurants() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(Arrays.asList(MY))));
    }

    @Test
    public void getEnabledRestaurantsUnauth() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized()));
    }

    @Test
    public void addVote() throws Exception{
        mockMvc.perform(put(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MY)))
                .andExpect(status().isOk());
        assertMatch(userService.getWithChoice(ADMIN_ID).getChoice(), votingService.getRestaurantById(MY.getId()));
    }
}