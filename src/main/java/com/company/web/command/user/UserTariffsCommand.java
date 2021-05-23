package com.company.web.command.user;

import com.company.Paths;
import com.company.Util;
import com.company.db.dao.TariffDao;
import com.company.db.dao.UserDao;
import com.company.db.dao.mysql.MySqlDaoFactory;
import com.company.db.entity.Tariff;
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
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public class UserTariffsCommand extends Command {

    private static final Logger logger = LogManager.getLogger(UserTariffsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting service command");
        TariffDao tariffDao = MySqlDaoFactory.getDaoFactory().getTariffDao();
        String forward = Paths.PAGE_ERROR_PAGE;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Map<Tariff,LocalDate> tariffsDates;
        try {
            tariffsDates = tariffDao.getUserTariffsAndExpiryDates(user.getAgreementNumber(),
                    Util.determineLocale(session,request));
            request.setAttribute("tariffsDates", tariffsDates);
        } catch (SQLException e) {
            e.printStackTrace();
            return forward;
        }

        return Paths.PAGE_USER_TARIFFS;
    }
}
