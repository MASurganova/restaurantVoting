package ru.voting.repository.MockRepositories;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.voting.model.Dish;
import ru.voting.repository.DishRepository;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryDishRepository extends AbstractInMemoryRepository<Dish> implements DishRepository {

    private static final Logger log =getLogger(InMemoryDishRepository.class);

    public InMemoryDishRepository() {
        super();
    }

}
