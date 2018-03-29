package ru.voting.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.voting.model.Dish;
import ru.voting.repository.DishRepository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcDishRepositoryImpl implements DishRepository {

    private static final BeanPropertyRowMapper<Dish> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Dish.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertDish;

    @Autowired
    public JdbcDishRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertDish = new SimpleJdbcInsert(dataSource)
                .withTableName("dishes")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM dishes WHERE id=?", id) != 0;
    }

    @Override
    public Dish save(Dish dish) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", dish.getId())
                .addValue("description", dish.getDescription())
                .addValue("price", dish.getPrice())
                .addValue("restaurant", dish.getRestaurantId());

        if (dish.isNew()) {
            Number newKey = insertDish.executeAndReturnKey(map);
            dish.setId(newKey.intValue());
        } else {
            if (namedParameterJdbcTemplate.update(
                    "UPDATE dishes SET description=:description, price=:price, " +
                            "restaurant_id=:restaurant WHERE id=:id", map) == 0)
                return null;
        }
        return dish;
    }

    @Override
    public Dish get(int id) {
        List<Dish> dishes = jdbcTemplate.query("SELECT * FROM dishes WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(dishes);
    }

    @Override
    public List<Dish> getAll() {
        return jdbcTemplate.query("SELECT * FROM dishes ORDER BY restaurant_id, id", ROW_MAPPER);
    }
}