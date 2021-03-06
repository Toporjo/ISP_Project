package com.company.db.dao;


import com.company.db.entity.Tariff;
import com.company.db.entity.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 *  Interface for realizing user DAO
 */
public interface UserDao {

    void insert(User user) throws SQLException;

    void update(User user) throws SQLException;

    void delete(User user) throws SQLException;


    User findUserByAgreementNumber(int agreementNumber) throws SQLException;

//    List<User> getAllUsers() throws SQLException;


    void changeUserBlock(int id,boolean block) throws SQLException;

    boolean checkIfUserHasTariff(int agreementNumber, int tariffId, int serviceId) throws SQLException;

    void insertUserTariffRelation(int agreementNumber, int tariffId, LocalDate expiryDate) throws SQLException;

    Map<User, Double> getAllUsersWithExpiredPayment(LocalDate date) throws SQLException;

    void updateUserAndExpiryDates(User x, LocalDate date) throws SQLException;

    double getUserPaymentSize(int agreementNumber, LocalDate todayDate) throws SQLException;

    int getUsersNumber() throws SQLException;

    List<User> getAllUsers(int page, int pageSize) throws SQLException;

}
