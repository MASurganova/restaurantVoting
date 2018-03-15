package ru.voting.repository;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.voting.util.RestaurantUtil;
import ru.voting.model.Dish;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class DishRepository extends AbstractInMemoryRepository<Dish> {

    private static final Logger log =getLogger(DishRepository.class);

    public DishRepository() {
    }


    //    public boolean delete(int id) {
//        log.info("delete {}", id);
//        return true;
//    }
//
//    public Dish save(Dish dish) {
//        log.info("save {}", dish);
//        return dish;
//    }
//
//    public Dish get(int id) {
//        log.info("get {}", id);
//        return null;
//    }
//
//    public List<Dish> getAll() {
//        log.info("getAll");
//        return Collections.emptyList();
//    }

}
