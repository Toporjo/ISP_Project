package com.company.web.command.common;

import com.company.Paths;
import com.company.db.dao.DaoFactory;
import com.company.db.dao.UserDao;
import com.company.web.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User registration command
 */

public class RegisterCommand extends Command {

    private static final Logger logger = Logger.getLogger(RegisterCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting register command");
        String forward = Paths.PAGE_LOGIN;
        String errorMessage;
        UserDao userDAO = DaoFactory.getDaoFactory("mysql").getUserDao();
        String name = request.getParameter("fname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConf = request.getParameter("password_conf");
        String languageId = request.getParameter("language");

        return forward;
    }

    private boolean validateName(String name) {
        if (!name.matches("^\\p{L}{2,20} \\p{L}{2,20}[ ]{0,20}$")) {
            return true;
        }
        if (name.length() > 40) {
            return true;
        }
        name = name.trim();
        return false;
    }

    private boolean validatePassword(String password, String passwordConf) {
        if(!password.equals(passwordConf)){
            return false;
        }
        return !password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,20}$");
    }

    private boolean validateEmail(String email){
        return !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }


}
