


package ru.voting.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.voting.model.User;
import ru.voting.to.UserTo;
import ru.voting.util.UserUtil;

import java.util.List;

@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController extends AbstractUserController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(UserTo userTo) {
        if (userTo.isNew()) {
            super.create(UserUtil.createNewFromTo(userTo));
        } else super.update(userTo, userTo.getId());
    }
}
