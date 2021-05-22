package com.company.web;
import com.company.task.DailyPaymentCheckingTask;
import com.company.web.command.Command;
import com.company.web.command.CommandContainer;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *Main servlet controller
 * Front controller
 * @see Command
 */
public class ServletController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ServletController.class);




    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException, ServletException {
        handler(httpServletRequest,httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handler(req,resp);
    }


    private void handler(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Executing handler");
        logger.trace("Request locale - " +request.getLocale());

        request.setAttribute("requestLocale", request.getLocale());

        String forward = null;
        String commandName = request.getParameter("command");

        Command command = CommandContainer.get(commandName);

        if(command !=null) {
            forward = command.execute(request, response);
        }

        if(forward != null){
            RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
            dispatcher.forward(request,response);
        }
        logger.debug("Handler done");
    }

    @Override
    public void init() throws ServletException {

    }

    @Override
    public void destroy() {

    }




}