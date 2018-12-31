package ru.voting.repository;

import ru.voting.model.VotingEvent;

import java.time.LocalDate;
import java.util.List;

public interface  HistoryRepository {

    boolean delete(LocalDate date);

    VotingEvent save(VotingEvent event) ;

    VotingEvent get(LocalDate date);

    List<VotingEvent> getAll();
}