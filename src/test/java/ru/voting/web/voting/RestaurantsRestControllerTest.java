package ru.voting.web.voting;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.voting.TestUtil;
import ru.voting.model.Dish;
import ru.voting.model.Restaurant;
import ru.voting.service.VotingService;
import ru.voting.web.AbstractControllerTest;
import ru.voting.web.json.JsonUtil;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.TestData.*;

public class RestaurantsRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = RestaurantsRestController.REST_URL + '/';


    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MY.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MY));
    }

    @Test
    public void testGetAllRestaurants()throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MY, OTHER)));
    }

    @Test
    public void testCreate() throws Exception {
        Restaurant expected = new Restaurant(null, "New");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Restaurant returned = TestUtil.readFromJson(action, Restaurant.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(votingService.getAllRestaurants(), MY,expected, OTHER);
    }

    @Test
    public void testUpdate() throws Exception{
        Restaurant updated = new Restaurant(MY);
        updated.setName("UpdatedName");
        mockMvc.perform(put(REST_URL + MY.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(votingService.getRestaurantById(MY.getId()), updated);
    }

    @Test
    public void testDelete() throws Exception{
        mockMvc.perform(delete(REST_URL + OTHER.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(votingService.getAllRestaurants(), MY);
    }

    @Test
    public void testAddRestaurantToVote() throws Exception{
        Restaurant updated = new Restaurant(OTHER);
        mockMvc.perform(put(REST_URL + OTHER.getId() + "/enabled")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        updated.setEnabled(true);
        assertMatch(votingService.getCurrentRestaurants(), MY, updated);
    }

    @Test
    //    Почему-то не создается - у ресторана так и остается два блюда
    public void testCreateDish() throws Exception{
        Dish expected = new Dish("new", 120);
        expected.setRestaurant(MY);
        ResultActions action = mockMvc.perform(post(REST_URL + MY.getId() + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Dish returned = TestUtil.readFromJson(action, Dish.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(votingService.getRestaurantById(MY.getId()).getLunch(), DISH_1, DISH_2, expected);
    }

    @Test
//    Почему-то не удаляется - у ресторана так и остается два блюда
    public void testDeleteDish() throws Exception {
        mockMvc.perform(delete(REST_URL + MY.getId() + "/" + DISH_1.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(votingService.getRestaurantByIdWithLunch(MY.getId()).getLunch(), DISH_2);
    }

    @Test
    public void testUpdateDish() throws Exception{
        Dish updated = new Dish(DISH_1);
        updated.setDescription("updated");
        mockMvc.perform(put(REST_URL + MY.getId() + "/" + DISH_1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(votingService.getDishById(DISH_1.getId()), updated);
    }

    @Test
    public void testEnd() throws Exception{
        mockMvc.perform(put(REST_URL + "end"))
                .andDo(print())
                .andExpect(status().isOk());
        Assert.assertEquals(votingService.getCurrentRestaurants(), Collections.emptyList());
        Assert.assertEquals(votingService.getRestaurantById(MY.getId()).getVoters(), 0);
    }
}