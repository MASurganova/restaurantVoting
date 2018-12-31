package ru.voting.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.voting.model.Dish;
import ru.voting.model.VotingEvent;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudHistoryRepository extends JpaRepository<VotingEvent, LocalDate> {

    @Transactional
    int removeVotingEventByDate(LocalDate date);

    @Override
    @Transactional
    VotingEvent save(VotingEvent event);

    @Override
    List<VotingEvent> findAll(Sort sort);

    VotingEvent getByDate(LocalDate date);

}
