package ru.voting.web.voting;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.voting.model.VotingEvent;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ajax/admin/history")
public class HistoryAjaxController extends AbstractVotingController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VotingEvent> getHistory() {
        return super.getHistory();
    }

    @DeleteMapping("/{date}")
    public void deleteVotingEvent(@PathVariable("date") String date) {
        super.deleteVotingEvent(LocalDate.parse(date));
    }

}
