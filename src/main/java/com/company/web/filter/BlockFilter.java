package com.company.web.filter;

import com.company.Paths;
import com.company.Util;
import com.company.db.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class BlockFilter implements Filter {

    private static List<String> unrestricted = new ArrayList<>();


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession(false);
        User user;
        if (session == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        user = (User) session.getAttribute("user");
        if (user == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (unrestricted.contains(req.getParameter("command"))) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if(user.isBlocked()){
            servletRequest.setAttribute("errorMessage", "У вас недостатньо коштів");
            servletRequest.getRequestDispatcher(Paths.PAGE_ERROR_PAGE)
                    .forward(servletRequest, servletResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        unrestricted = Util.asList(filterConfig.getInitParameter("unrestricted"));
    }
}

//V-2
//package com.company.web.filter;
//
//        import com.company.Paths;
//        import com.company.Util;
//        import com.company.db.entity.User;
//
//        import javax.servlet.*;
//        import javax.servlet.http.HttpServletRequest;
//        import javax.servlet.http.HttpServletResponse;
//        import javax.servlet.http.HttpSession;
//        import java.io.IOException;
//        import java.util.ArrayList;
//        import java.util.List;
//        import java.util.Set;
//
//
//public class BlockFilter implements Filter {
//
//    private static List<String> unrestricted = new ArrayList<>();
//
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpSession session = req.getSession(false);
//        Set<Integer> blockedIds = (Set<Integer>) req.getServletContext().getAttribute("blockedIds");
//        Set<Integer> unblockedIds = (Set<Integer>) req.getServletContext().getAttribute("unblockedIds");
//        User user;
//        if (session == null) {
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }
//
//        user = (User) session.getAttribute("user");
//        if (user == null) {
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }
//
//        if (unrestricted.contains(req.getParameter("command"))) {
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }
//
//        if (user.isBlocked()) {
//            if (unblockedIds.remove(user.getAgreementNumber())) {
//                user.setBlocked(false);
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            }
//            blockedIds.remove(user.getAgreementNumber());
//        } else {
//            if (blockedIds.remove(user.getAgreementNumber())) {
//                user.setBlocked(true);
//            } else {
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            }
//        }
//
//        servletRequest.setAttribute("errorMessage", "У вас недостатньо коштів");
//        servletRequest.getRequestDispatcher(Paths.PAGE_ERROR_PAGE)
//                .forward(servletRequest, servletResponse);
//
//    }
//
//
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        unrestricted = Util.asList(filterConfig.getInitParameter("unrestricted"));
//    }
//}

//V-1
//
//package com.company.web.filter;
//
//        import com.company.Paths;
//        import com.company.Util;
//        import com.company.db.entity.User;
//
//        import javax.servlet.*;
//        import javax.servlet.http.HttpServletRequest;
//        import javax.servlet.http.HttpServletResponse;
//        import javax.servlet.http.HttpSession;
//        import java.io.IOException;
//        import java.util.ArrayList;
//        import java.util.List;
//        import java.util.Set;
//
//
//public class BlockFilter implements Filter {
//
//    private static List<String> unrestricted = new ArrayList<>();
//
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpSession session = req.getSession(false);
//        Set<Integer> blockedIds = (Set<Integer>) req.getServletContext().getAttribute("blockedIds");
//        Set<Integer> unblockedIds = (Set<Integer>) req.getServletContext().getAttribute("unblockedIds");
//        if (unrestricted.contains(req.getParameter("command"))){
//            filterChain.doFilter(servletRequest,servletResponse);
//            return;
//        }
//        if (session == null){
//            filterChain.doFilter(servletRequest,servletResponse);
//            return;
//        }
//        User user = (User) session.getAttribute("user");
//        if (user == null){
//            filterChain.doFilter(servletRequest,servletResponse);
//            return;
//        }
//
//
//        if(user.isBlocked() && unblockedIds.remove(user.getAgreementNumber())){
//            user.setBlocked(false);
//            session.setAttribute("user",user);
//            filterChain.doFilter(servletRequest,servletResponse);
//            return;
//        }
//
//        if(user.isBlocked() || blockedIds.remove(user.getAgreementNumber())){
//            user.setBlocked(true);
//            session.setAttribute("user",user);
//            servletRequest.setAttribute("errorMessage", "У вас недостатньо коштів");
//
//            servletRequest.getRequestDispatcher(Paths.PAGE_ERROR_PAGE)
//                    .forward(servletRequest, servletResponse);
//        }
//        filterChain.doFilter(servletRequest,servletResponse);
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        unrestricted = Util.asList(filterConfig.getInitParameter("unrestricted"));
//    }
//}
//
