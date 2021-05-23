package com.company.web.command.common;

import com.company.Paths;
import com.company.db.dao.TariffDao;
import com.company.db.dao.UserDao;
import com.company.db.dao.mysql.MySqlTariffDao;
import com.company.db.dao.mysql.MySqlUserDao;
import org.apache.log4j.helpers.LogLog;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceCommandTest {



    @Mock
    TariffDao tariffDao;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @BeforeAll
    public static void beforeAll() {
        LogLog.setQuietMode(true);
    }

    @BeforeEach
    public void before() throws NoSuchFieldException, IllegalAccessException {
        Field instance = MySqlTariffDao.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, tariffDao);
    }

    @AfterEach
    public void AfterEach() throws NoSuchFieldException, IllegalAccessException {
        Field instance = MySqlTariffDao.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void shouldForwardToErrorPageWhenServiceIdIsInvalid() throws IOException, ServletException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("order")).thenReturn(null);
        when(request.getParameter("orderBy")).thenReturn(null);
        when(session.getAttribute("tariffSortParams")).thenReturn(null);
        when(request.getParameter("service")).thenReturn("1a");

        ServiceCommand serviceCommand = new ServiceCommand();
        String actualPage = serviceCommand.execute(request,response);

        assertEquals(Paths.PAGE_ERROR_PAGE,actualPage);
    }
}