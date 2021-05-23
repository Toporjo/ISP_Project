package com.company.db.dao.mysql;

import com.company.db.DBUtil;
import com.company.db.dao.UserDao;
import com.company.db.entity.User;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class MySqlUserDaoTest{

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String URL_CONNECTION = "jdbc:mysql://localhost:3306/?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false&user=root&password=haU7hu6Q";
    private static final String URL_CONNECTION_WITH_DB = "jdbc:mysql://localhost:3306/isp_test?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false&user=root&password=haU7hu6Q&allowMultiQueries=true";
    private static final String USER = "root";
    private static final String PASS = "haU7hu6Q";
    private static User testUser;
    private static List<User> testUserList;


    @BeforeAll
    public static void beforeAll() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        try (Connection con = DriverManager.getConnection(URL_CONNECTION, USER, PASS);
             Statement statement = con.createStatement()) {
            String sql = "create database IF NOT EXISTS isp_test;";
            statement.executeUpdate(sql);

            sql = "use isp_test;";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS users (\n" +
                    "  agreement_number int NOT NULL AUTO_INCREMENT,\n" +
                    "  frist_name varchar(45) NOT NULL,\n" +
                    "  last_name varchar(45) NOT NULL,\n" +
                    "  patronymic_name varchar(45) DEFAULT NULL,\n" +
                    "  password varchar(100) NOT NULL,\n" +
                    "  blocked tinyint(1) DEFAULT '0',\n" +
                    "  balance decimal(10,2) DEFAULT '0.00',\n" +
                    "  role_id int NOT NULL,\n" +
                    "  language_id int NOT NULL,\n" +
                    "  PRIMARY KEY (agreement_number),\n" +
                    "  UNIQUE KEY id_UNIQUE (agreement_number)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS `services` (\n" +
                    "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                    "  `service_name` varchar(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE KEY `service_name_UNIQUE` (`service_name`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8";
            statement.executeUpdate(sql);

            sql = "CREATE TABLE IF NOT EXISTS `tariffs` (\n" +
                    "  `id` int NOT NULL AUTO_INCREMENT,\n" +
                    "  `price` decimal(10,2) NOT NULL,\n" +
                    "  `discount` tinyint NOT NULL DEFAULT '0',\n" +
                    "  `service_id` int NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  KEY `fk_tariffs_services1_idx` (`service_id`),\n" +
                    "  CONSTRAINT `fk_tariffs_services1` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8";

            statement.executeUpdate(sql);

            sql = "CREATE TABLE `users_tariffs` (\n" +
                    "  `user_id` int NOT NULL,\n" +
                    "  `tariff_id` int NOT NULL,\n" +
                    "  `expiry_date` date NOT NULL,\n" +
                    "  PRIMARY KEY (`user_id`,`tariff_id`),\n" +
                    "  KEY `fk_users_has_tariffs_tariffs1_idx` (`tariff_id`),\n" +
                    "  KEY `fk_users_has_tariffs_users1_idx` (`user_id`),\n" +
                    "  CONSTRAINT `fk_users_has_tariffs_tariffs1` FOREIGN KEY (`tariff_id`) REFERENCES `tariffs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT `fk_users_has_tariffs_users1` FOREIGN KEY (`user_id`) REFERENCES `users` (`agreement_number`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8";

            statement.executeUpdate(sql);

        }
    }

    @BeforeEach
    public void beforeEach() throws SQLException{
        testUser = new User(1,"Test Name", "Test Last Name", "Test Patronymic Name", "password",  0, false, 0, 0);
        testUserList = new ArrayList<>(Arrays.asList(
                new User(2,"Test Name 1", "Test Last Name 1", "Test Patronymic Name 1", "password1",  0, false, 0, 0),
                new User(3,"Test Name 2", "Test Last Name 2", "Test Patronymic Name 2", "password2",  0, false, 0, 1),
                new User(4,"Test Name 3", "Test Last Name 3", "Test Patronymic Name 3", "password3",  0, false, 0, 0),
                new User(5,"Test Name 4", "Test Last Name 4", "Test Patronymic Name 4", "password4",  0, false, 0, 1)
        ));
        try (Connection con = DriverManager.getConnection(URL_CONNECTION_WITH_DB, USER, PASS);
             Statement statement = con.createStatement()) {
            String sql;
            sql = "INSERT INTO users (frist_name, last_name, patronymic_name, password,  blocked, balance, role_id, language_id) " +
                    "VALUES ('Test Name', 'Test Last Name', 'Test Patronymic Name', 'password',  '0', '0', '0', '0');\n";
            statement.executeUpdate(sql);

            sql = "INSERT INTO services ( service_name) VALUES ('Telephone');"; //id = 1
            statement.executeUpdate(sql);

            sql = "INSERT INTO services ( service_name) VALUES ('Internet');";//id = 2
            statement.executeUpdate(sql);

            sql = "INSERT INTO tariffs (price, discount, service_id) VALUES ('200', '0', '1');\n"; //id = 1
            statement.executeUpdate(sql);

            sql = "INSERT INTO tariffs (price, discount, service_id) VALUES ('400', '0', '2');\n"; //id = 2
            statement.executeUpdate(sql);

            sql = "INSERT INTO users_tariffs (user_id, tariff_id, expiry_date) VALUES ('1', '1', '*');\n".replace("*", LocalDate.now().minusDays(1).toString());
            statement.executeUpdate(sql);

            sql = "INSERT INTO users_tariffs (user_id, tariff_id, expiry_date) VALUES ('1', '2', '*');\n".replace("*", LocalDate.now().plusDays(1).toString());
            statement.executeUpdate(sql);


        }

    }

    @AfterEach
    public void afterEach() throws SQLException{
        try (Connection con = DriverManager.getConnection(URL_CONNECTION_WITH_DB, USER, PASS);
             Statement statement = con.createStatement()) {
            String sql;
            sql = "DELETE FROM users;";
            statement.executeUpdate(sql);

            sql = "DELETE FROM tariffs;";
            statement.executeUpdate(sql);

            sql = "DELETE FROM users_tariffs;";
            statement.executeUpdate(sql);

            sql = "DELETE FROM services;";
            statement.executeUpdate(sql);

            sql = "ALTER TABLE users AUTO_INCREMENT = 1";
            statement.executeUpdate(sql);

            sql = "ALTER TABLE tariffs AUTO_INCREMENT = 1";
            statement.executeUpdate(sql);

            sql = "ALTER TABLE services AUTO_INCREMENT = 1";
            statement.executeUpdate(sql);
        }
    }

    @AfterAll
    public static void afterAll(){
        try (Connection con = DriverManager.getConnection(URL_CONNECTION, USER, PASS);
             Statement statement = con.createStatement()) {
            String sql = "DROP DATABASE IF EXISTS isp_test;";
            statement.executeUpdate(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        return  DriverManager.getConnection(URL_CONNECTION_WITH_DB, USER, PASS);
    }

    @Test
    public void shouldInsertUser() throws SQLException {
        User testUser = new User("Ivan","Ivanov","Ivanovych",
                "password",100,false,0,0);
        try (MockedStatic<DBUtil> utilities = Mockito.mockStatic(DBUtil.class)) {
            utilities.when(DBUtil::getConnection).thenReturn(getConnection());
            MySqlUserDao.getInstance().insert(testUser);
        }catch (SQLException e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void shouldGetUserByAgreementNumber() throws SQLException{
        User resultUser;
        try (MockedStatic<DBUtil> utilities = Mockito.mockStatic(DBUtil.class)) {
            utilities.when(DBUtil::getConnection).thenReturn(getConnection());
            resultUser = MySqlUserDao.getInstance().findUserByAgreementNumber(testUser.getAgreementNumber());
        }
        assertEquals(testUser,resultUser);
    }


    @Test
    public void shouldChangeUserBlock() throws SQLException{
        User resultUser;
        try (MockedStatic<DBUtil> utilities = Mockito.mockStatic(DBUtil.class)) {
            utilities.when(DBUtil::getConnection).thenAnswer(x->getConnection());
            UserDao userDao = MySqlUserDao.getInstance();
            userDao.changeUserBlock(testUser.getAgreementNumber(),true);
            resultUser = userDao.findUserByAgreementNumber(testUser.getAgreementNumber());
        }
        assertNotEquals(testUser.isBlocked(),resultUser.isBlocked());
    }

    @Test
    public void shouldUpdateUser() throws SQLException{
        User resultUser;
        try (MockedStatic<DBUtil> utilities = Mockito.mockStatic(DBUtil.class)) {
            utilities.when(DBUtil::getConnection).thenAnswer(x->getConnection());
            UserDao userDao = MySqlUserDao.getInstance();
            User copyUser = new User(1,"Test Name", "Test Last Name", "Test Patronymic Name", "password",  0, false, 0, 0);
            copyUser.setBalance(1000);
            userDao.update(copyUser);
            resultUser = userDao.findUserByAgreementNumber(testUser.getAgreementNumber());
        }
        assertNotEquals(testUser.getBalance(), resultUser.getBalance(), 0.0);
    }

    @Test
    public void shouldGetAllUsersOnPage() throws SQLException{
        List<User> resultUserList;
        try (MockedStatic<DBUtil> utilities = Mockito.mockStatic(DBUtil.class)) {
            utilities.when(DBUtil::getConnection).thenAnswer(x->getConnection());
            UserDao userDao = MySqlUserDao.getInstance();
            for (User user : testUserList) {
                userDao.insert(user);
            }
            resultUserList = userDao.getAllUsers(2,2);
        }
        testUserList.add(0,testUser);
        testUserList = testUserList.stream().skip(4).limit(2).collect(Collectors.toList());

        for (int a=0;a<testUserList.size();a++){
            if (!testUserList.get(a).equals(resultUserList.get(a))){
                fail();
            }
        }
    }

    @Test
    public void shouldReturnPaymentSize() throws SQLException{
        double expectedPrice = 200;
        double actualPrice=0;
        try (MockedStatic<DBUtil> utilities = Mockito.mockStatic(DBUtil.class)) {
            utilities.when(DBUtil::getConnection).thenAnswer(x->getConnection());
            UserDao userDao = MySqlUserDao.getInstance();
            actualPrice = userDao.getUserPaymentSize(testUser.getAgreementNumber(),LocalDate.now());
        }
        assertEquals(expectedPrice, actualPrice, 0.0);
    }


}
//name="jdbc/isp"
//            auth="Container"
//            type="javax.sql.DataSource"
//            maxActive="100"
//            maxIdle="30"
//            maxWait="10000"
//            username="root"
//            password="haU7hu6Q"
//            driverClassName="com.mysql.cj.jdbc.Driver"
//            url="jdbc:mysql://localhost:3306/isp?useUnicode=true&amp;serverTimezone=UTC&amp;useSSL=true&amp;verifyServerCertificate=false&amp;useAffectedRows=true"/>


//package com.epam.rd.java.basic.practice8;
//
//import com.epam.rd.java.basic.practice8.db.DBManager;
//import com.epam.rd.java.basic.practice8.db.entity.User;
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import javax.jws.soap.SOAPBinding;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Properties;
//
//public class Part1StudentTest {
//
//    private static final String JDBC_DRIVER = "org.h2.Driver";
//    private static final String DB_URL = "jdbc:h2:~/test";
//    private static final String URL_CONNECTION = "jdbc:h2:~/test;user=youruser;password=yourpassword;";
//    private static final String USER = "youruser";
//    private static final String PASS = "yourpassword";
//
//    private static DBManager dbManager;
//
//    @BeforeClass
//    public static void beforeTest() throws SQLException, ClassNotFoundException {
//        Class.forName(JDBC_DRIVER);
//
//        try (OutputStream output = new FileOutputStream("app.properties")) {
//            Properties prop = new Properties();
//            prop.setProperty("connection.url", URL_CONNECTION);
//            prop.store(output, null);
//        } catch (IOException io) {
//            io.printStackTrace();
//        }
//
//        dbManager = DBManager.getInstance();
//
//        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
//             Statement statement = con.createStatement()) {
//            String sql = "CREATE TABLE IF NOT EXISTS users (\n" +
//                    "  id INTEGER(11) NOT NULL AUTO_INCREMENT,\n" +
//                    " login VARCHAR(20) NOT NULL, \n" +
//                    "  PRIMARY KEY (id));";
//
//            statement.executeUpdate(sql);
//        }
//    }
//
//
//    @Test
//    public void shouldInsertUser(){
//        User user = new User();
//        user.setLogin("user1");
//
//        dbManager.insertUser(user);
//
//        Assert.assertEquals(dbManager.getUser(user.getLogin()).getLogin(),"user1");
//
//    }
//
//
//
//
//
//}