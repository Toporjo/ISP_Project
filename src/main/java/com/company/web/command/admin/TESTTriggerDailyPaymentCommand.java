package com.company.web.command.admin;

import com.company.Paths;
import com.company.db.dao.DaoFactory;
import com.company.db.dao.UserDao;
import com.company.db.entity.User;
import com.company.task.DailyPaymentCheckingTask;
import com.company.web.command.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class TESTTriggerDailyPaymentCommand extends Command {

    private static final Logger logger = LogManager.getLogger(TESTTriggerDailyPaymentCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String forward = Paths.PAGE_HOME;

        Thread thread = new Thread(new DailyPaymentCheckingTask(request.getServletContext()));
        thread.start();
        logger.debug("Daily payment command triggered");
        request.setAttribute("successMessage","DAILY PAYMENT TRIGGERED");
        return forward;
    }
}
