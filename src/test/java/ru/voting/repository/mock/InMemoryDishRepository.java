package ru.voting.repository.mock;

import org.springframework.stereotype.Repository;
import ru.voting.TestData;
import ru.voting.model.Dish;
import ru.voting.repository.DishRepository;

import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryDishRepository extends AbstractInMemoryRepository<Dish> implements DishRepository {

    public InMemoryDishRepository() {
        super(getLogger(InMemoryDishRepository.class));
        init();
    }

    public void init() {
        inMemoryRepository.clear();
        inMemoryRepository.put(TestData.DISH_1.getId(), TestData.DISH_1);
        inMemoryRepository.put(TestData.DISH_2.getId(), TestData.DISH_2);
        inMemoryRepository.put(TestData.DISH_3.getId(), TestData.DISH_3);
        inMemoryRepository.put(TestData.DISH_4.getId(), TestData.DISH_4);
        inMemoryRepository.put(TestData.DISH_5.getId(), TestData.DISH_5);
        inMemoryCount = new AtomicInteger(100004);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        return super.delete(id);
    }

    @Override
    public Dish save(Dish dish, int restaurantId) {
        return super.save(dish);
    }

    @Override
    public Dish get(int id, int restaurantId) {
        return super.get(id);
    }
}
