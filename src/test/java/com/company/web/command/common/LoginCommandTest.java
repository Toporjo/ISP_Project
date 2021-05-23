package com.company.web.command.common;

import com.company.Paths;
import com.company.db.dao.UserDao;
import com.company.db.dao.mysql.MySqlUserDao;
import com.company.db.entity.User;
import org.apache.log4j.helpers.LogLog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class LoginCommandTest {

    @Mock
    UserDao userDao;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @BeforeAll
    public static void beforeAll(){
        LogLog.setQuietMode(true);
    }

    @BeforeEach
    public void before() throws NoSuchFieldException, IllegalAccessException {
        Field instance = MySqlUserDao.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, userDao);
    }

    @AfterEach
    public void AfterEach() throws NoSuchFieldException, IllegalAccessException {
        Field instance = MySqlUserDao.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void shouldLoginUser() throws IOException, ServletException {
        final String agreementNumber = "";
        final String password = "";

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("agreementNumber")).thenReturn(agreementNumber);
        when(request.getParameter("password")).thenReturn(password);


        LoginCommand loginCommand = new LoginCommand();
        String resultPage = loginCommand.execute(request,response);
        assertEquals(Paths.PAGE_LOGIN,resultPage);

    }

    @Test
    public void shouldForwardToLoginWhenNumberAndPasswordAreEmpty() throws IOException, ServletException {
        final String agreementNumber = "";
        final String password = "";

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("agreementNumber")).thenReturn(agreementNumber);
        when(request.getParameter("password")).thenReturn(password);


        LoginCommand loginCommand = new LoginCommand();
        String resultPage = loginCommand.execute(request,response);
        assertEquals(Paths.PAGE_LOGIN,resultPage);
    }

    @Test
    public void shouldForwardToErrorPageWhenAgreementNumberIsInvalid() throws IOException, ServletException {
        final String agreementNumber = "234f";
        final String password = "qwerty";

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("agreementNumber")).thenReturn(agreementNumber);
        when(request.getParameter("password")).thenReturn(password);


        LoginCommand loginCommand = new LoginCommand();
        String resultPage = loginCommand.execute(request,response);
        assertEquals(Paths.PAGE_ERROR_PAGE,resultPage);
    }

    @Test
    public void shouldForwardToErrorPageWhenPasswordIsIncorrect() throws SQLException, IOException, ServletException {
        final int reqAgreementNumber = 1;
        final String reqAgreementNumberStr = "1";
        final String reqPassword = "qwerty";



        User user = new User();
        user.setAgreementNumber(reqAgreementNumber);
        user.setPassword("wrongPassword");

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("agreementNumber")).thenReturn(reqAgreementNumberStr);
        when(request.getParameter("password")).thenReturn(reqPassword);
        when(userDao.findUserByAgreementNumber(reqAgreementNumber)).thenReturn(user);


        LoginCommand loginCommand = new LoginCommand();
        String resultPage = loginCommand.execute(request,response);
        assertEquals(Paths.PAGE_ERROR_PAGE,resultPage);
        verify(request,Mockito.atMostOnce()).setAttribute("errorMessage",Paths.ERROR_NO_SUCH_USER);
    }

    @Test
    public void shouldRedirectToHomePageWhenCredentialsAreValid() throws SQLException, IOException, ServletException {
        final int reqAgreementNumber = 1;
        final String reqAgreementNumberStr = "1";
        final String reqPassword = "qwerty";



        User user = new User();
        user.setAgreementNumber(reqAgreementNumber);
        user.setPassword(reqPassword);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("agreementNumber")).thenReturn(reqAgreementNumberStr);
        when(request.getParameter("password")).thenReturn(reqPassword);
        when(userDao.findUserByAgreementNumber(reqAgreementNumber)).thenReturn(user);


        LoginCommand loginCommand = new LoginCommand();
        String resultPage = loginCommand.execute(request,response);

        verify(response,Mockito.atMostOnce()).sendRedirect(Paths.COMMAND_HOME);
        assertNull(resultPage);
    }

}
