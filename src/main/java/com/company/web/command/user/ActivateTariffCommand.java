package com.company.web.command.user;

import com.company.Paths;
import com.company.db.dao.UserDao;
import com.company.db.dao.mysql.MySqlDaoFactory;
import com.company.db.entity.User;
import com.company.web.command.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class ActivateTariffCommand extends Command {

    private static final Logger logger = LogManager.getLogger(ActivateTariffCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting service command");
        UserDao userDao = MySqlDaoFactory.getDaoFactory().getUserDao();
        String forward;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String tariffId = request.getParameter("tariffId");
        String serviceId = request.getParameter("serviceId");


        try {
            boolean userHasTariff = userDao.checkIfUserHasTariff(user.getAgreementNumber(),Integer.parseInt(tariffId),Integer.parseInt(serviceId));
            if(userHasTariff){
                request.setAttribute("errorMessage","You already have tariff!");
                forward = Paths.PAGE_ERROR_PAGE;
                return forward;
            }
            userDao.insertUserTariffRelation(user.getAgreementNumber(), Integer.parseInt(tariffId), LocalDate.now().plusMonths(1));
        } catch (SQLException e) {
            e.printStackTrace();
            forward = Paths.PAGE_LOGIN;
            return forward;
        }

//        if(!userHasTariff){
//            userDao.setTariffForUser(user.getAgreementNumber(),Integer.parseInt(tariffId));
//        }






        response.sendRedirect(Paths.COMMAND_HOME);
        return null;
    }
}
