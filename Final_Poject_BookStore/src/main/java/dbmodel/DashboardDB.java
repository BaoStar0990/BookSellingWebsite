package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import model.StatusOrder;
import model.StatusPayment;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DashboardDB {
    // Get total revenue by time range
    public static double getTotalRevenue(String timeRange) {
        return getAggregateValue("SELECT SUM(od.unitPrice * od.quantity) FROM OrderDetail od JOIN od.bill b WHERE b.statusPayment = :status AND b.orderDate >= :startDate", timeRange);
    }

    // Get total profit by time range
    public static double getTotalProfit(String timeRange) {
        return getAggregateValue("SELECT SUM((od.unitPrice - od.book.costPrice) * od.quantity) FROM OrderDetail od JOIN od.bill b WHERE b.statusPayment = :status AND b.orderDate >= :startDate", timeRange);
    }

    // Get aggregate value helper method
    private static double getAggregateValue(String query, String timeRange) {
        EntityManager em = null;
        try {
            em = DBUtil.getEmFactory().createEntityManager();
            var preparedQuery = em.createQuery(query, Double.class);
            preparedQuery.setParameter("status", StatusPayment.Paid);
            preparedQuery.setParameter("startDate", getStartDateByTimeRange(timeRange));
            Double result = preparedQuery.getSingleResult();
            return result != null ? result : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Get number of customers
    public static long getNumberOfCustomers() {
        return getCount("SELECT COUNT(c) FROM Customer c");
    }

    // Get number of books
    public static long getNumberOfBooks() {
        return getCount("SELECT COUNT(b) FROM Book b");
    }

    // Get number of orders by status
    public static long getNumberOfOrdersByStatus(StatusOrder status, String timeRange) {
        String query = "SELECT COUNT(b) FROM Bill b WHERE b.orderDate >= :startDate";
        if (status != null) {
            query += " AND b.statusOrder = :status";
        }
        return getCount(query, status, timeRange);
    }

    // Get number of orders
    public static long getNumberOfOrders() {
        return getCount("SELECT COUNT(b) FROM Bill b");
    }

    // Get count helper method
    private static long getCount(String query) {
        return getCount(query, null, null);
    }

    private static long getCount(String query, StatusOrder status, String timeRange) {
        EntityManager em = null;
        try {
            em = DBUtil.getEmFactory().createEntityManager();
            var preparedQuery = em.createQuery(query, Long.class);
            if (status != null) {
                preparedQuery.setParameter("status", status);
            }
            if (timeRange != null) {
                preparedQuery.setParameter("startDate", getStartDateByTimeRange(timeRange));
            }
            Long result = preparedQuery.getSingleResult();
            return result != null ? result : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Get top 5 best-selling books
    public static Map<String, Long> getTopFiveBestSellingBooks(String timeRange) {
        return getTopFive("SELECT b.title, SUM(od.quantity) AS NumberOfCopy FROM OrderDetail od JOIN od.book b JOIN od.bill bl WHERE bl.statusPayment = :status AND b.title IS NOT NULL AND bl.orderDate >= :startDate GROUP BY b.title ORDER BY NumberOfCopy DESC", timeRange);
    }

    // Get top 5 favorite authors
    public static Map<String, Long> getTopFiveFavoriteAuthors(String timeRange) {
        return getTopFive("SELECT a.name, SUM(od.quantity) AS SoldQuantity FROM Author a JOIN a.books b JOIN b.orderDetails od JOIN od.bill bl WHERE bl.statusPayment = :status AND a.name IS NOT NULL AND bl.orderDate >= :startDate GROUP BY a.name ORDER BY SoldQuantity DESC", timeRange);
    }

    // Get top 5 helper method
    private static Map<String, Long> getTopFive(String query, String timeRange) {
        EntityManager em = null;
        try {
            em = DBUtil.getEmFactory().createEntityManager();
            var preparedQuery = em.createQuery(query, Object[].class);
            preparedQuery.setParameter("status", StatusPayment.Paid);
            preparedQuery.setParameter("startDate", getStartDateByTimeRange(timeRange));
            List<Object[]> results = preparedQuery.setMaxResults(5).getResultList();
            Map<String, Long> topItems = new LinkedHashMap<>();
            for (Object[] result : results) {
                String name = (String) result[0];
                Long count = ((Number) result[1]).longValue();
                topItems.put(name, count);
            }
            return topItems;
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedHashMap<>();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Get revenue by category
    public static Map<String, Double> getRevenueByCategory(String timeRange) {
        return getRevenue("SELECT c.name, SUM(od.unitPrice * od.quantity) AS Revenue FROM Category c JOIN c.books b JOIN b.orderDetails od JOIN od.bill bl WHERE bl.statusPayment = :status AND bl.orderDate >= :startDate GROUP BY c.name ORDER BY Revenue DESC", timeRange);
    }

    // Get revenue by time range
    public static Map<String, Double> getRevenueByTimeRange(String timeRange) {
        return getRevenue("SELECT DATE_FORMAT(bl.orderDate, '%Y-%m-%d') AS Date, SUM(od.unitPrice * od.quantity) AS Revenue FROM OrderDetail od JOIN od.bill bl WHERE bl.statusPayment = :status AND bl.orderDate >= :startDate GROUP BY Date ORDER BY Date ASC", timeRange);
    }

    // Get revenue by campaign
    public static Map<String, Double> getRevenueByCampaign(String timeRange) {
        return getRevenue("SELECT dc.campaignName, SUM(od.unitPrice * od.quantity) AS Revenue FROM DiscountCampaign dc JOIN dc.books b JOIN b.orderDetails od JOIN od.bill bl WHERE bl.statusPayment = :status AND bl.orderDate >= :startDate GROUP BY dc.campaignName ORDER BY Revenue DESC", timeRange);
    }

    // Get revenue helper method
    private static Map<String, Double> getRevenue(String query, String timeRange) {
        EntityManager em = null;
        try {
            em = DBUtil.getEmFactory().createEntityManager();
            var preparedQuery = em.createQuery(query, Object[].class);
            preparedQuery.setParameter("status", StatusPayment.Paid);
            preparedQuery.setParameter("startDate", getStartDateByTimeRange(timeRange));
            List<Object[]> results = preparedQuery.getResultList();
            Map<String, Double> revenueMap = new LinkedHashMap<>();
            for (Object[] result : results) {
                String name = (String) result[0];
                Double revenue = result[1] != null ? (Double) result[1] : 0.0; // Handle null values
                revenueMap.put(name, revenue);
            }
            return revenueMap;
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedHashMap<>();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Get start date by time range
    private static LocalDate getStartDateByTimeRange(String timeRange) {
        LocalDate startDate = LocalDate.now();
        switch (timeRange) {
            case "7days":
                startDate = startDate.minusDays(7);
                break;
            case "1month":
                startDate = startDate.minusMonths(1);
                break;
            case "3months":
                startDate = startDate.minusMonths(3);
                break;
            case "6months":
                startDate = startDate.minusMonths(6);
                break;
            case "1year":
                startDate = startDate.minusYears(1);
                break;
            case "all":
                startDate = LocalDate.of(2024, 1, 1); 
                break;
        }
        return startDate;
    }
}
