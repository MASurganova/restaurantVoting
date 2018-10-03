package ru.voting.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.voting.service.VotingService;

@Controller
@RequestMapping(value = "/voting")
public class VotingController {

    @Autowired
    VotingService service;


}
