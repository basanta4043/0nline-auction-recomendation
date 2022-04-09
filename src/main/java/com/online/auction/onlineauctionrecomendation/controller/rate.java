package com.online.auction.onlineauctionrecomendation.controller;

import com.online.auction.onlineauctionrecomendation.model.AuctionEntity;
import com.online.auction.onlineauctionrecomendation.model.NotificationEntity;
import com.online.auction.onlineauctionrecomendation.model.RatingEntity;
import com.online.auction.onlineauctionrecomendation.model.UserEntity;
import com.online.auction.onlineauctionrecomendation.service.AuctionService;
import com.online.auction.onlineauctionrecomendation.service.NotificationService;
import com.online.auction.onlineauctionrecomendation.service.RatingService;
import com.online.auction.onlineauctionrecomendation.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "rate",urlPatterns = "/rate.do")
public class rate extends HttpServlet {

    /* all actions of post follows a post/redirect/get pattern in order to avoid form resubmission */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RatingService ratingService = new RatingService();
        String next_page = "/user/homepage.jsp";
        HttpSession session = request.getSession();

        long from_id = ((UserEntity) session.getAttribute("user")).getUserId();
        long to_id = Long.parseLong(request.getParameter("to_id").split("/")[0]);
        long aid = Long.parseLong(request.getParameter("aid").split("/")[0]);
        AuctionEntity auction = new AuctionService().getAuction(aid);
        switch (request.getParameter("action")) {
            case "addRating":
                int rating = Integer.parseInt(request.getParameter("rating"));

                /* check if the rating goes to seller */
                Byte forSeller;
                if (to_id == auction.getSellerId()) {
                    forSeller = 1;
                } else {
                    forSeller = 0;
                }
                /* add the rating */
                RatingEntity ratingEntity = new RatingEntity(from_id, to_id, aid, rating, forSeller);
                ratingService.addEntity(ratingEntity);
                /* add a notification for this rating */
                NotificationService notificationService = new NotificationService();
                NotificationEntity notificationEntity = new NotificationEntity(to_id, "rate", aid, from_id);
                notificationService.addEntity(notificationEntity);

                String url = "/online-auction/rate.do?action=getRating&to_id=" + to_id + "&aid=" + aid;
                response.sendRedirect(url);
                return;
            case "updateRating":
                rating = Integer.parseInt(request.getParameter("rating"));
                ratingService.updateRating(from_id, to_id, aid, rating);

                url = "/online-auction/rate.do?action=getRating&to_id=" + to_id + "&aid=" + aid;
                response.sendRedirect(url);
                return;
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = "/user/homepage.jsp";

        HttpSession session = request.getSession();
        RatingService ratingService = new RatingService();
        UserService userService = new UserService();

        long to_id, from_id, aid;
        from_id = ((UserEntity) session.getAttribute("user")).getUserId();
        if (request.getParameter("action") == null) {
            RequestDispatcher view = request.getRequestDispatcher(next_page);
            view.forward(request, response);
        }
        String param = request.getParameter("action");
        switch (param) {
            case "getRating":
                to_id = Long.parseLong(request.getParameter("to_id"));
                aid = Long.parseLong(request.getParameter("aid"));

                Integer rating = null;
                RatingEntity ratingEntity = ratingService.getRating(from_id, to_id, aid);
                if (ratingEntity != null) {
                    rating = ratingEntity.getRating();
                }
                UserEntity user = userService.getUser(to_id);

                request.setAttribute("aid", aid);
                request.setAttribute("to_id", to_id);
                request.setAttribute("to_user", user);
                request.setAttribute("from_id", from_id);
                request.setAttribute("rating", rating);
                next_page = "/user/rating.jsp";
                break;
            case "listFrom": case "listTo":
                List<RatingEntity> ratingsLst;
                if (param.equals("listFrom")) {
                    ratingsLst = ratingService.getFromOrToRatings(from_id, RatingService.Rating_t.To_t);
                } else {
                    ratingsLst = ratingService.getFromOrToRatings(from_id, RatingService.Rating_t.From_t);
                }
                List<UserEntity> sendersOrReceiversLst = new ArrayList<>();
                List<AuctionEntity> auctionsLst = new ArrayList<>();
                AuctionService auctionService = new AuctionService();

                double avg_rating = 0;
                int total_reputation = 0;
                for (RatingEntity r : ratingsLst) {
                    switch (r.getRating()) {
                        case 1: total_reputation += 1; break;
                        case 2: total_reputation += 3; break;
                        case 3: total_reputation += 5; break;
                        case 4: total_reputation += 10; break;
                        case 5: total_reputation += 20; break;
                    }
                    avg_rating += r.getRating();
                    if (param.equals("listFrom")) {
                        sendersOrReceiversLst.add(userService.getUser(r.getFromId()));
                    } else {
                        sendersOrReceiversLst.add(userService.getUser(r.getToId()));
                    }
                    auctionsLst.add(auctionService.getAuction(r.getAuctionId()));
                }
                if (ratingsLst.size() > 0) {
                    avg_rating = avg_rating / ratingsLst.size();
                    DecimalFormat df = new DecimalFormat("0.0");
                    request.setAttribute("avg_rating", Double.parseDouble(df.format(avg_rating)));
                    request.setAttribute("total_reputation", total_reputation);
                } else {
                    request.setAttribute("avg_rating", null);
                    request.setAttribute("total_reputation", null);
                }
                request.setAttribute("ratingsLst", ratingsLst);
                if (param.equals("listFrom")) {
                    request.setAttribute("sendersLst", sendersOrReceiversLst);
                    next_page = "/user/listYourReceivedRatings.jsp";
                } else {
                    request.setAttribute("receiversLst", sendersOrReceiversLst);
                    next_page = "/user/listYourSubmittedRatings.jsp";
                }
                request.setAttribute("auctionsLst", auctionsLst);
                break;
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

}
