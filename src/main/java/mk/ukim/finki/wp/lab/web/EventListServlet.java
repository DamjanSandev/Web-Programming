package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Event;
import mk.ukim.finki.wp.lab.service.impl.EventServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EventListServlet", urlPatterns = {"", "/test", "/test/searchByText", "/test/searchByRating"})
public class EventListServlet extends HttpServlet {
    private final EventServiceImpl eventService;
    protected final SpringTemplateEngine templateEngine;

    public EventListServlet(EventServiceImpl eventService, SpringTemplateEngine templateEngine) {
        this.eventService = eventService;
        this.templateEngine = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext webContext = new WebContext(webExchange);

        webContext.setVariable("events", this.eventService.listAll());

        this.templateEngine.process("listEvents.html", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext webContext = new WebContext(webExchange);
        String path = req.getServletPath();
        List<Event> events = null;

        if (path.equals("/test/searchByText")) {
            String text = req.getParameter("text");
            events = this.eventService.searchEvent(text);
            webContext.setVariable("textSearchResults", events);
        } else if (path.equals("/test/searchByRating")) {
            double rating = Double.parseDouble(req.getParameter("rating"));
            events = this.eventService.listAll().stream()
                    .filter(event -> event.getPopularityScore() >= rating)
                    .toList();
            webContext.setVariable("ratingSearchResults", events);
        } else {
            events = this.eventService.listAll();
            webContext.setVariable("events", events);
        }

        this.templateEngine.process("listEvents.html", webContext, resp.getWriter());
    }
}
