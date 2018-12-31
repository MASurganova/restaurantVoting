package ru.voting.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.voting.model.VotingEvent;
import ru.voting.repository.HistoryRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Repository
public class DataJpaHistoryRepositoryImpl implements HistoryRepository{

    private static final Sort SORT_DATE = new Sort(Sort.Direction.ASC, "date");

    @Autowired
    CrudHistoryRepository crudHistoryRepository;

    @Override
    public boolean delete(LocalDate date) {
        return crudHistoryRepository.removeVotingEventByDate(date) != 0;
    }

    @Override
    public VotingEvent save(VotingEvent event) {
        return crudHistoryRepository.save(event);
    }

    @Override
    public VotingEvent get(LocalDate date) {
        return crudHistoryRepository.getByDate(date);
    }

    @Override
    public List<VotingEvent> getAll() {
        return crudHistoryRepository.findAll(SORT_DATE);
    }
}
