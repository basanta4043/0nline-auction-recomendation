package com.online.auction.onlineauctionrecomendation.model;

import com.online.auction.onlineauctionrecomendation.service.AuctionService;
import com.online.auction.onlineauctionrecomendation.service.BidService;

import java.util.*;

public class RecommendationEngine {

    private int K_Neighbours = 2;
    private int Items_to_recommend = 5;

    public List<AuctionEntity> getRecommendations(long uid) {
        List<Long> knn = getKnn(uid);
        return getItems(uid, knn);
    }


    private List<Long> getKnn(long uid) {
        BidService bidService = new BidService();
        AuctionService auctionService = new AuctionService();
        Map<Long, Integer> k_nearestSet = new TreeMap<>();

        List<Long> userBids = bidService.getAllUserBids(uid);
        for (Long aid : userBids) {
            HashSet<Long> bidderIds = auctionService.getUniqueBidders(aid);
            bidderIds.remove((Long) uid);
            for (Long bidderId : bidderIds) {
                int count = k_nearestSet.containsKey(bidderId) ? k_nearestSet.get(bidderId) : 0;
                k_nearestSet.put(bidderId, count + 1);
            }
        }
        SortedSet<Map.Entry<Long, Integer>> desc_k_nearest = entriesSortedByValues(k_nearestSet);
        int i = 0;
        List<Long> knn = new ArrayList<>();
        for (Map.Entry<Long, Integer> it : desc_k_nearest) {
            knn.add(it.getKey());
            if (++i > K_Neighbours) {
                break;
            }
        }
        System.out.println("knn:::" + knn);
        return knn;
    }

    private List<AuctionEntity> getItems(long uid, List<Long> knn) {
        List<AuctionEntity> recommendationsLst = new ArrayList<>();
        BidService bidService = new BidService();
        AuctionService auctionService = new AuctionService();
        List<Long> excludeBids = bidService.getAllUserBids(uid);
        List<AuctionEntity> excludeAuctions = auctionService.getAllAuctions(uid, false);
        int items = 0;
        for (Long n : knn) {
            if (items > Items_to_recommend) {
                break;
            }
            List<Long> biddedItems = bidService.getAllUserBids(n);
            biddedItems.removeAll(excludeBids);
            for (Long it : biddedItems) {
                if (items > Items_to_recommend) {
                    break;
                }
                AuctionEntity auction = auctionService.getAuction(it);
                if (auction.getIsActive() == (byte) 0 || containsId(excludeAuctions, it)) {
                    continue;
                }
                recommendationsLst.add(auction);
                items++;
            }
        }
        System.out.println(recommendationsLst.size());
        return recommendationsLst;
    }

    private boolean containsId(List<AuctionEntity> list, long id) {
        for (AuctionEntity object : list) {
            if (object.getAuctionId() == id) {
                return true;
            }
        }
        return false;
    }


    private static <K, V extends Comparable<? super V>>
    SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(
                new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                        int res = e2.getValue().compareTo(e1.getValue());
                        return res != 0 ? res : 1;
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

}

