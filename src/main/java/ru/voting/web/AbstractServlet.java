package ru.voting.web;

import org.springframework.context.ConfigurableApplicationContext;
import ru.voting.service.VotingService;
import ru.voting.util.ValidationUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class AbstractServlet extends HttpServlet {
    protected ConfigurableApplicationContext springContext;

    protected VotingService service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        springContext = ValidationUtil.getSpringContext();
        service = springContext.getBean(VotingService.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }
}
