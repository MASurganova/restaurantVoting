package ru.voting.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.User;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {

    @Transactional
    int removeById(int id);

    @Override
    @Transactional
    User save(User user);

    @Override
    Optional<User> findById(Integer id);

    @Override
    @EntityGraph(attributePaths = "choice")
    List<User> findAll(Sort sort);

    @Query("SELECT distinct u FROM User u")
    List<User> getAll(Sort sort);

    User getByEmail(String email);

    @EntityGraph(attributePaths = "choice")
    Optional<User> getById(int id);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.name=:name, u.email=:email, u.password=:password WHERE u.id=:id")
    void update(@Param("id") Integer id, @Param("name") String name, @Param("email") String email, @Param("password") String password);

}
