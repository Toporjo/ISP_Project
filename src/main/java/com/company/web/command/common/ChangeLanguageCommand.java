package com.company.web.command.common;

import com.company.Paths;
import com.company.db.constant.Language;
import com.company.db.dao.DaoFactory;
import com.company.db.dao.UserDao;
import com.company.db.entity.User;
import com.company.web.command.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.jws.soap.SOAPBinding;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class ChangeLanguageCommand extends Command {

    private static final Logger logger = LogManager.getLogger(ChangeLanguageCommand.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting home command ");
        String forward = Paths.PAGE_HOME;
        String errorMessage;
        HttpSession session = request.getSession();
        UserDao userDao = DaoFactory.getDaoFactory().getUserDao();
        String languageId = request.getParameter("languageId");

        try {
            int langId = Integer.parseInt(languageId);
            User user = (User) session.getAttribute("user");
            if(user != null){
                user.setLanguageId(langId);
                userDao.update(user);
            }
            session.setAttribute("locale", Language.values()[langId]);

        }catch (NumberFormatException nfe){
            request.setAttribute("errorMessage","Wrong language");
            return Paths.PAGE_ERROR_PAGE;
        } catch (SQLException e) {
            request.setAttribute("errorMessage","Failed to change language");
            return Paths.PAGE_ERROR_PAGE;
        }


        return forward;
    }
}
