package ru.voting.repository.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.voting.model.Restaurant;
import ru.voting.repository.RestaurantRepository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private static final BeanPropertyRowMapper<Restaurant> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Restaurant.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertRestaurant;

    @Autowired
    public RestaurantRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertRestaurant = new SimpleJdbcInsert(dataSource)
                .withTableName("restaurants")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM restaurants WHERE id=?", id) != 0;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", restaurant.getId())
                .addValue("name", restaurant.getName())
                .addValue("enabled", restaurant.isEnabled())
                .addValue("voters", restaurant.getVoters());

        if (restaurant.isNew()) {
            Number newKey = insertRestaurant.executeAndReturnKey(map);
            restaurant.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE restaurants SET name=:name, enabled=:enabled, voters=:voters" +
                            " WHERE id=:id", map);
        }
        return restaurant;
    }

    @Override
    public Restaurant get(int id) {
        List<Restaurant> restaurants = jdbcTemplate.query("SELECT * FROM restaurants WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(restaurants);
    }

    @Override
    public List<Restaurant> getAll() {
        return jdbcTemplate.query("SELECT * FROM restaurants ORDER BY name", ROW_MAPPER);
    }


    @Override
    public Restaurant getByName(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM restaurants WHERE name=?", ROW_MAPPER, name);
     }

    @Override
    public List<Restaurant> getEnabledRestaurants() {
        return jdbcTemplate.query("SELECT * FROM restaurants WHERE enabled=true ORDER BY name", ROW_MAPPER);
    }

}
