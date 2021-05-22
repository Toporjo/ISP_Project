package com.company.web.filter;

import com.company.db.dao.UserDao;
import com.company.db.dao.mysql.MySqlDaoFactory;
import com.company.db.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UserUpdateFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession(false);
        Set<Integer> updatedIds = (Set<Integer>) req.getServletContext().getAttribute("updatedIds");

        if(session == null){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        User user = (User) session.getAttribute("user");

        if(user != null && updatedIds.remove(user.getAgreementNumber())){
            UserDao userDao = MySqlDaoFactory.getDaoFactory().getUserDao();
            try {
                user = userDao.findUserByAgreementNumber(user.getAgreementNumber());
                session.setAttribute("user",user);
            } catch (SQLException e) {
                e.printStackTrace();
                session.invalidate();
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Updated ids container initialized");
        filterConfig.getServletContext().setAttribute("updatedIds", Collections.synchronizedSet(new HashSet<Integer>()));

    }
}
