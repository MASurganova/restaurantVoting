package ru.voting.web;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.voting.service.VotingService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class AbstractServlet extends HttpServlet {

    protected VotingService service;

    protected WebApplicationContext springContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        if (springContext == null) springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        if (service == null) service = springContext.getBean(VotingService.class);
    }

}
