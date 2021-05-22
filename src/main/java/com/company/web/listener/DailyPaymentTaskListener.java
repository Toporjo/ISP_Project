package com.company.web.listener;

import com.company.task.DailyPaymentCheckingTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DailyPaymentTaskListener implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("blockedIds", Collections.synchronizedSet(new HashSet<Integer>()));
        sce.getServletContext().setAttribute("unblockedIds", Collections.synchronizedSet(new HashSet<Integer>()));
        ScheduledExecutorService scheduled;
        scheduled = Executors.newSingleThreadScheduledExecutor();
        if(Boolean.parseBoolean(sce.getServletContext().getInitParameter("enableDailyPayment"))) {
            scheduled.scheduleAtFixedRate(new DailyPaymentCheckingTask(sce.getServletContext()),
                    20,
                    20,
                    TimeUnit.SECONDS);
            System.out.println("Payment Task Started");
        }

//ScheduledExecutorService scheduled;
//        scheduled = Executors.newSingleThreadScheduledExecutor();
//        scheduled.scheduleAtFixedRate(new DailyPaymentCheckingTask(),
//                Util.getEndOfDay(Date.from(Instant.now()),Calendar.getInstance()).getTime(),
//                1,
//                TimeUnit.DAYS);
//        System.out.println("Payment Task Started");
//
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();
    }
}
