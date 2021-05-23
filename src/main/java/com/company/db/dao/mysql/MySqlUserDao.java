package com.company.db.dao.mysql;

import com.company.db.DBUtil;
import com.company.db.EntityMapper;
import com.company.db.Fields;
import com.company.db.dao.UserDao;
import com.company.db.entity.Tariff;
import com.company.db.entity.User;

import javax.jws.soap.SOAPBinding;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlUserDao implements UserDao {

    private static UserDao instance;

    public static synchronized UserDao getInstance() {
        if (instance == null) {
            instance = new MySqlUserDao();
        }
        return instance;
    }

    private MySqlUserDao() {
    }



    @Override
    public void insert(User user) throws SQLException {
        String query = "INSERT INTO users (frist_name, last_name, patronymic_name, password,role_id,language_id)" +
                " VALUES (?, ?, ?, ?,?,?);";
        PreparedStatement pstmt = null;
        try (Connection con = DBUtil.getConnection()){
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,user.getFirstName());
            pstmt.setString(2,user.getLastName());
            pstmt.setString(3,user.getPatronymicName());
            pstmt.setString(4,user.getPassword());
            pstmt.setInt(5,user.getRoleId());
            pstmt.setInt(6,user.getLanguageId());
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String query = "UPDATE users " +
                "SET frist_name = ?, last_name = ?, patronymic_name = ?, " +
                "password = ?, blocked = ?, balance = ?, language_id = ? " +
                "WHERE (agreement_number = ?);";
        PreparedStatement pstmt;
        try (Connection con = DBUtil.getConnection()){
            int ctr=1;
            pstmt = con.prepareStatement(query);
            pstmt.setString(ctr++,user.getFirstName());
            pstmt.setString(ctr++,user.getLastName());
            pstmt.setString(ctr++,user.getPatronymicName());
            pstmt.setString(ctr++,user.getPassword());
            pstmt.setBoolean(ctr++,user.isBlocked());
            pstmt.setDouble(ctr++,user.getBalance());
            pstmt.setInt(ctr++,user.getLanguageId());
            pstmt.setInt(ctr,user.getAgreementNumber());
            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void delete(User user) throws SQLException {

    }



    @Override
    public User findUserByAgreementNumber(int agreementNumber) throws SQLException {
        String query = "SELECT * FROM users where agreement_number = ?;";
        PreparedStatement statement;
        ResultSet rs;
        User user = new User();

        try(Connection con = DBUtil.getConnection()) {
            statement = con.prepareStatement(query);
            statement.setLong(1,agreementNumber);
            rs = statement.executeQuery();
            if (rs.next()) {
                UserMapper mapper = new UserMapper();
                user = mapper.mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

//    @Override
//    public List<User> getAllUsers() throws SQLException {
//        String query = "SELECT * FROM users";
//        Statement statement;
//        ResultSet rs;
//        List<User> users;
//        try(Connection con = DBUtil.getConnection()) {
//            statement = con.createStatement();
//            rs = statement.executeQuery(query);
//            users = new ArrayList<>();
//            UserMapper mapper = new UserMapper();
//            while (rs.next()) {
//                users.add(mapper.mapRow(rs));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw e;
//        }
//        return users;
//    }

    @Override
    public void changeUserBlock(int id,boolean block) throws SQLException {
        String query = "UPDATE users SET blocked = ? WHERE (agreement_number = ?);";
        PreparedStatement pstmt;
        try(Connection con = DBUtil.getConnection()) {
            pstmt = con.prepareStatement(query);
            pstmt.setBoolean(1,block);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public boolean checkIfUserHasTariff(int agreementNumber, int tariffId, int serviceId) throws SQLException {
        String query = "select * from users u\n" +
                "inner join users_tariffs ut on ut.user_id = u.agreement_number\n" +
                "inner join tariffs t on t.id = ut.tariff_id\n" +
                "where u.agreement_number = ? and t.service_id = ?;";
        PreparedStatement pstmt;
        ResultSet rs;
        try(Connection con = DBUtil.getConnection()) {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,agreementNumber);
            pstmt.setInt(2,serviceId);
            System.out.println(pstmt);
            rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void insertUserTariffRelation(int agreementNumber, int tariffId, LocalDate expiryDate) throws SQLException {

        String query = " INSERT INTO users_tariffs (user_id, tariff_id, expiry_date) " +
                "VALUES (?, ?, ?);";
        PreparedStatement pstmt;
        ResultSet rs;
        try(Connection con = DBUtil.getConnection()) {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,agreementNumber);
            pstmt.setInt(2,tariffId);
            pstmt.setDate(3,
                    Date.valueOf(expiryDate));
            System.out.println(pstmt.executeUpdate());

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public Map<User, Double> getAllUsersWithExpiredPayment(LocalDate date) throws SQLException {
        String query = "select u.*, SUM(price) as price_sum\n" +
                "from users u\n" +
                "inner join users_tariffs ut on u.agreement_number = ut.user_id\n" +
                "inner join tariffs t on ut.tariff_id = t.id\n" +
                "where role_id='0' and expiry_date <= ? and blocked='0'\n" +
                "group by agreement_number;";
        PreparedStatement pstmt;
        ResultSet rs;
        Map<User,Double> paymentUserMap = new HashMap<>();
        try(Connection con = DBUtil.getConnection()) {
            pstmt = con.prepareStatement(query);
            pstmt.setDate(1,Date.valueOf(date));
            rs = pstmt.executeQuery();
            UserMapper mapper = new UserMapper();
            while (rs.next()){
                User user = mapper.mapRow(rs);
                double payment = rs.getDouble("price_sum");
                paymentUserMap.put(user,payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return paymentUserMap;
    }

    @Override
    public void updateUserAndExpiryDates(User user, LocalDate date) throws SQLException {
        String query = "UPDATE users SET balance = ?,blocked = ? WHERE (agreement_number = ?);";
        PreparedStatement pstmt;
        Connection con = DBUtil.getConnection();;
        try{
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(query);
            pstmt.setDouble(1,user.getBalance());
            pstmt.setBoolean(2,user.isBlocked());
            pstmt.setInt(3,user.getAgreementNumber());
            pstmt.executeUpdate();
            if (!user.isBlocked()) {
                updateExpiryDatesByUserId(user.getAgreementNumber(), date, con);
            }
            con.commit();
        }catch (SQLException e){
            con.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            con.close();
        }
    }

    @Override
    public int getUserPaymentAmount(int userId,LocalDate todayDate) throws SQLException {
        String query = "select SUM(t.price) as payment from users_tariffs ut\n" +
                "inner join tariffs t on t.id = ut.tariff_id\n" +
                "where  ut.user_id = ? and ut.expiry_date<=?;\n";
        PreparedStatement pstmt;
        ResultSet rs;
        int paymentAmount = 0;
        try(Connection con = DBUtil.getConnection()) {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,userId);
            pstmt.setDate(2,Date.valueOf(todayDate));
            rs = pstmt.executeQuery();
            if (rs.next()){
                paymentAmount =  rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return paymentAmount;
    }

    @Override
    public double getUserPaymentSize(int agreementNumber, LocalDate todayDate) throws SQLException {
        String query = "select SUM(t.price) as payment from users_tariffs ut\n" +
                "inner join tariffs t on t.id = ut.tariff_id\n" +
                "where  ut.user_id = ? and ut.expiry_date<=?;\n";
        PreparedStatement pstmt;
        ResultSet rs;
        int paymentAmount = 0;
        try(Connection con = DBUtil.getConnection()) {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1,agreementNumber);
            pstmt.setDate(2,Date.valueOf(todayDate));
            rs = pstmt.executeQuery();
            if (rs.next()){
                paymentAmount =  rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return paymentAmount;
    }

    @Override
    public int getUsersNumber() throws SQLException {
            String query = "select COUNT(agreement_number) from users;\n";
            Statement stmt;
            ResultSet rs;
            int amount=0;
            try(Connection con = DBUtil.getConnection()) {
                stmt = con.createStatement();
                rs = stmt.executeQuery(query);
                if (rs.next()){
                    amount = rs.getInt(1);
                }
            }catch (SQLException e){
                e.printStackTrace();
                throw e;
            }
            return amount;
    }

    @Override
    public List<User> getAllUsers(int page, int pageSize) throws SQLException {
        String query = "select * from users\n" +
                "order by agreement_number\n" +
                "limit ?,?;";
        PreparedStatement statement;
        ResultSet rs;
        List<User> users = new ArrayList<>();
        try (Connection con = DBUtil.getConnection()) {
            statement = con.prepareStatement(query);
            statement.setInt(1, page*pageSize);
            statement.setInt(2, pageSize);

            rs = statement.executeQuery();
            UserMapper mapper = new UserMapper();
            while (rs.next()) {
                users.add(mapper.mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return users;
    }

    private void updateExpiryDatesByUserId(int userId,LocalDate date,Connection con)throws SQLException{
        String query = "UPDATE users_tariffs\n" +
                "SET expiry_date = ? \n" +
                "WHERE user_id = ? and expiry_date <= ?;";
        PreparedStatement pstmt;
         try {
            pstmt = con.prepareStatement(query);
             pstmt.setDate(1,Date.valueOf(date.plusMonths(1)));
             pstmt.setInt(2,userId);
             pstmt.setDate(3,Date.valueOf(date));
            pstmt.executeUpdate();
         } catch (SQLException e){
             e.printStackTrace();
             throw e;
         }

    }


    private static class UserMapper implements EntityMapper<User> {

        @Override
        public User mapRow(ResultSet rs) {
            try {
                User user = new User();
                user.setAgreementNumber(rs.getInt(Fields.USER_AGREEMENT_NUMBER));
                user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
                user.setLastName(rs.getString(Fields.USER_LAST_NAME));
                user.setPatronymicName(rs.getString(Fields.USER_PATRONYMIC_NAME));
                user.setPassword(rs.getString(Fields.USER_PASSWORD));
                user.setBalance(rs.getDouble(Fields.USER_BALANCE));
                user.setBlocked(rs.getBoolean(Fields.USER_BLOCKED));
                user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
                user.setLanguageId(rs.getInt(Fields.USER_LANGUAGE_ID));
                return user;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
