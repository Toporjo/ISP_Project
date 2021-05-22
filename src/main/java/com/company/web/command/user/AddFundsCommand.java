package com.company.web.command.user;

import com.company.Paths;
import com.company.Util;
import com.company.db.dao.UserDao;
import com.company.db.dao.mysql.MySqlDaoFactory;
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
import java.util.Set;

public class AddFundsCommand  extends Command {

    private static final Logger logger = LogManager.getLogger(AddFundsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("Starting service command");
        UserDao userDao = MySqlDaoFactory.getDaoFactory().getUserDao();
        String forward;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String amount = request.getParameter("amount");


        try {
            int fundsAmount = Integer.parseInt(amount);
            user.setBalance(Util.add(user.getBalance(),fundsAmount));



            double paymentSize = userDao.getUserPaymentSize(user.getAgreementNumber(),LocalDate.now());

            if(paymentSize != 0 && user.getBalance() >= paymentSize){
                user.setBlocked(false);
                user.setBalance(Util.subtract(user.getBalance(),paymentSize));
                userDao.updateUserAndExpiryDates(user,LocalDate.now());
            } else {
                userDao.update(user);
            }
            session.setAttribute("successMessage","Your balance was added " + fundsAmount+"₴");


        } catch (SQLException e) {
            e.printStackTrace();
            forward = Paths.PAGE_LOGIN;
            return forward;
        }

        ((Set<Integer>)request.getServletContext().getAttribute("updatedIds")).add(user.getAgreementNumber());
        response.sendRedirect(Paths.COMMAND_HOME);
        return null;
    }
}
