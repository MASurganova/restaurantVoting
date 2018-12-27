package ru.voting.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.voting.model.User;
import ru.voting.repository.UserRepository;

import java.util.List;

@Repository
public class DataJpaUserRepositoryImpl implements UserRepository {
    private static final Sort SORT_NAME_EMAIL = new Sort(Sort.Direction.ASC, "name", "email");

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Autowired
    private CrudRestaurantRepository crudRestaurantRepository;


    @Override
    public User save(User user) {
        return crudUserRepository.save(user);
    }

    @Override
    public boolean delete(int id) {
        return crudUserRepository.removeById(id) != 0;
    }

    @Override
    public User get(int id) {
        return crudUserRepository.findById(id).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return crudUserRepository.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return crudUserRepository.findAll(SORT_NAME_EMAIL);
    }

    @Override
    public User getWithChoice(int id) {
        return crudUserRepository.getById(id).orElse(null);
    }

    @Override
    public User save(User user, Integer restaurantId) {
        user.setChoice(restaurantId == null ? null : crudRestaurantRepository.getOne(restaurantId));
        return crudUserRepository.save(user);
    }

    @Override
    public void update(User user) {
        crudUserRepository.update(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }
}
