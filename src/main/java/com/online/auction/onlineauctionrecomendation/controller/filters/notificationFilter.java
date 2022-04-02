package com.online.auction.onlineauctionrecomendation.controller.filters;

import com.online.auction.onlineauctionrecomendation.model.NotificationEntity;
import com.online.auction.onlineauctionrecomendation.model.UserEntity;
import com.online.auction.onlineauctionrecomendation.service.NotificationService;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * A filter to find new notifications
 */
public class notificationFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /* get all the notifications and add them to a list */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        NotificationService notificationService = new NotificationService();
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user != null) {
            long rid = user.getUserId();
            List<NotificationEntity> notifList = notificationService.getNotificationsOf(rid);
            request.setAttribute("notifLst", notifList);
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {}

}
