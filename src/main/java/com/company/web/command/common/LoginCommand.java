package com.company.web.command.common;

import com.company.Paths;
import com.company.Validators;
import com.company.db.constant.Language;
import com.company.db.constant.Role;
import com.company.db.dao.DaoFactory;
import com.company.db.dao.UserDao;
import com.company.db.entity.User;
import com.company.web.command.Command;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;


/**
 * User login command
 */

public class LoginCommand extends Command {

    private static final Logger logger = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting login command");
        UserDao userDAO = DaoFactory.getDaoFactory().getUserDao();

        HttpSession session = request.getSession();

        String agreementNumber = request.getParameter("agreementNumber");
        String password = request.getParameter("password");

        String errorMessage;
        String forward = Paths.PAGE_ERROR_PAGE;

        if (agreementNumber == null || password == null || agreementNumber.isEmpty() || password.isEmpty()) {
            logger.debug("Email or password empty. Sending forward to login again");
            forward = Paths.PAGE_LOGIN;
            return forward;
        }
        if (!Validators.validateAgreementNumber(agreementNumber)){
            logger.debug("Invalid agreement number. Sending forward to login again");
            forward = Paths.PAGE_LOGIN;
            return forward;
        }

        User user = null;
        try {
            user = userDAO.findUserByAgreementNumber(Integer.parseInt(agreementNumber));
        } catch (SQLException throwables) {
            logger.error("Failed to get user", throwables);
        } catch (NumberFormatException nfe){
            logger.error("Wrong serial number format",nfe);
            logger.trace("Login failed. Data doesn't matches");
            errorMessage = Paths.ERROR_NO_SUCH_AGREEMENT_NUMBER;
            request.setAttribute("errorMessage", errorMessage);
            return forward;
        }

        if (user == null || !password.equals(user.getPassword())) {
            logger.trace("Login failed. Data doesn't matches");
            errorMessage = Paths.ERROR_NO_SUCH_USER;
            request.setAttribute("errorMessage", errorMessage);
            return forward;
        }

        logger.trace("Login successful");

        ((Set<Integer>)request.getServletContext().getAttribute("blockedIds"))
                .remove(user.getAgreementNumber());
        ((Set<Integer>)request.getServletContext().getAttribute("unblockedIds"))
                .remove(user.getAgreementNumber());


        session.setAttribute("user", user);
        session.setAttribute("role",Role.values()[user.getRoleId()]);


        logger.debug("Setting user locale");

        Language userLocaleName = Language.values()[user.getLanguageId()];

        if (userLocaleName != null) {
            Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", userLocaleName.getIsoCode());
            session.setAttribute("locale", userLocaleName);

        }
        logger.trace("User locale - " + userLocaleName);

        logger.debug("Login done. Sending redirect");

        response.sendRedirect(Paths.COMMAND_HOME);
        return null;
    }
}
