package com.company.web.command.common;

import com.company.Paths;
import com.company.db.dao.DaoFactory;
import com.company.db.dao.UserDao;
import com.company.db.entity.User;
import com.company.web.command.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 *Command for showing home page
 *Implements pagination and sorting for event entities
 *
 */

public class HomeCommand extends Command {
    private  static final int pageSize = 12;
    private  static final int paginationRange = 4;

    private static final Logger logger = LogManager.getLogger(HomeCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting home command ");
        String forward = Paths.PAGE_HOME;
        String errorMessage;
        HttpSession session = request.getSession();
        UserDao userDao = DaoFactory.getDaoFactory().getUserDao();

        try {
            User userTest = userDao.findUserByAgreementNumber(2);
            request.setAttribute("user",userTest);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return Paths.PAGE_ERROR_PAGE;
        }

        logger.debug("Done. Sending forward");
        return forward;
    }







}
