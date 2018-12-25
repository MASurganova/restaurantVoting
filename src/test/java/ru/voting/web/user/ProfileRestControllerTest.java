package ru.voting.web.user;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import ru.voting.TestUtil;
import ru.voting.model.Role;
import ru.voting.model.User;
import ru.voting.to.UserTo;
import ru.voting.util.UserUtil;
import ru.voting.web.AbstractControllerTest;
import ru.voting.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.voting.TestData.*;
import static ru.voting.web.user.ProfileRestController.REST_URL;

public class ProfileRestControllerTest extends AbstractControllerTest {

    @Test
    public void testGet() throws Exception {
        TestUtil.print(
                mockMvc.perform(get(REST_URL))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(contentJson(USER))
        );
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL))
                .andExpect(status().isOk());
        assertMatch(userService.getAll(), ADMIN);
    }

    @Test
    public void testUpdate() throws Exception {
        UserTo updated = new UserTo(USER_ID, "newName", "newemail@ya.ru", "newPassword");
        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());

        assertMatch(userService.getByEmail("newemail@ya.ru"), UserUtil.updateFromTo(new User(USER), updated));
    }

    @GetMapping(value = "/text")
    public String testUTF() {
        return "Русский текст";
    }
}