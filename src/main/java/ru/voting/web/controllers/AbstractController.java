package ru.voting.web.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public abstract class AbstractController {

    protected int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
