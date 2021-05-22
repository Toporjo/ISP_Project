package com.company.task;

import com.company.Paths;
import com.company.Util;
import com.company.db.dao.UserDao;
import com.company.db.dao.mysql.MySqlDaoFactory;
import com.company.db.entity.User;
import com.company.web.ServletController;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletContext;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DailyPaymentCheckingTask implements Runnable {

    private ServletContext context;

    public DailyPaymentCheckingTask(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        System.out.println("Scheduler started");
        Map<User,Double> paymentUserMap;
        UserDao userDao = MySqlDaoFactory.getDaoFactory().getUserDao();
        try {
            paymentUserMap= userDao.getAllUsersWithExpiredPayment(LocalDate.now());
            paymentUserMap.forEach((key, value) -> {
                if (key.getBalance() < value) {
                    key.setBlocked(true);
                    return;
                }
                key.setBalance(Util.subtract(key.getBalance(),value));
            });
            LocalDate date = LocalDate.now().plusMonths(1);
            paymentUserMap.keySet().forEach(x-> {
                try {
                    userDao.updateUserAndExpiryDates(x,date);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });


            Set<Integer> blocked = (Set<Integer>) context.getAttribute("updatedIds");
            blocked.addAll(paymentUserMap
                    .keySet()
                    .stream()
                    .map(User::getAgreementNumber)
                    .collect(Collectors.toList()));
//            blocked.addAll(paymentUserMap
//                    .keySet()
//                    .stream()
//                    .filter(x->x.isBlocked())
//                    .map(User::getAgreementNumber)
//                    .collect(Collectors.toSet()));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("Scheduler ended");
    }
}
