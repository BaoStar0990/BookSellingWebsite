package dbmodel;

import database.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import model.Address;
import model.Bill;
import model.Book;
import model.Customer;
import model.StatusOrder;
import org.hibernate.TransientObjectException;

public class CustomerDB  extends ModifyDB<Customer> implements DBInterface<Customer>{
    public static CustomerDB getInstance(){
        return new CustomerDB();
    }
    @Override
    public List<Customer> selectAll() { 
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<Customer> rs = em.createQuery("from Customer", Customer.class).getResultList();
            return rs;
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }   
    }

    @Override
    public Customer selectByID(Object id) {
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()) {
            return em.createQuery("from Customer c where c.id =: id ", Customer.class)
                    .setParameter("id", id).getSingleResult();    
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }
    }
    
    public Set<Address> getAddressCustomer(Customer c){
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<Address> listAddress = em.createQuery("from Address a where a.customer = :customer", 
                     Address.class).setParameter("customer", c).getResultList();
            // mọi phần tử trùng lặp sẽ bị bỏ
            Set<Address> rs = new HashSet<>(listAddress);
            return rs;
        }
        catch (TransientObjectException ex) {
            return null;
        } 
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        } 
    }
    
    public Set<Bill> getBillsCustomer(Customer c){
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            List<Bill> listBill = em.createQuery("from Bill b where b.customer = :customer", 
                     Bill.class).setParameter("customer", c).getResultList();
            // mọi phần tử trùng lặp sẽ bị bỏ
            Set<Bill> rs = new HashSet<>(listBill);
            return rs;
        }
        catch (TransientObjectException ex) {
            return null;
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }
    }
    public Customer checkExistEmail(String email){
        List<Customer> customers = selectAll();
        for(Customer c : customers){
            if(c.getEmail().equals(email)){
                return c;
            }
        }
        return null;
    }
    public boolean updateCustomer(Customer c){
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            em.getTransaction().begin();
            em.merge(c);
            em.getTransaction().commit();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public Customer selectCustomerByEmailPassWord(String email,String password){
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            Customer customer = em.createQuery("from Customer b where b.email =: email " + "and b.password =: password", Customer.class).
                    setParameter("email",email).setParameter("password",password).getResultList().get(0);
            // mọi phần tử trùng lặp sẽ bị bỏ
            return  customer;
        }
        catch (TransientObjectException ex) {
            return null;
        }
        catch(NoResultException ex){
            return null;
        }
        catch(Exception ex){
            return null;
        }
    }
    public boolean makeAnOrder(Bill cart, Customer customer){
        EntityManager em = null;
        EntityTransaction tr = null;
        try{
            em = DBUtil.getEmFactory().createEntityManager();
            tr = em.getTransaction();
            tr.begin();
            // kiểm tra trong cart có sp nào không, hoặc có phải là cart không
            if(cart.getOrderDetails()==null || !cart.getStatusOrder().toString().equals("storing"))
                return false;
            // kiểm tra số lượng đặt có vượt quá số lượng trong kho
            boolean isExceedQuantity = cart.getOrderDetails().stream()
                    .anyMatch(o -> o.getBook().getStocks() < o.getQuantity());
            if(isExceedQuantity)
                return false;
            // chuyển trạng thái từ "storing" sang "Processing"
            cart.setStatusOrder(StatusOrder.Processing);
            // tạo cart mới cho khách hàng         
            Bill newCart = new Bill();
            newCart.setCustomer(customer);
            newCart.setStatusOrder(StatusOrder.Storing);
            em.persist(newCart);              
            // trừ số lượng sách trong kho khi đặt
            final EntityManager emFinal = em;           
            cart.getOrderDetails().forEach(o ->{
                        // lấy cuốn sách cập nhật số lượng trong kho
                        var book = emFinal.find(Book.class, o.getBook().getId());
                        book.setStocks(book.getStocks() - o.getQuantity());
                        emFinal.merge(book);
                    });
            // cập nhật cart thành đơn hàng
            em.merge(cart);   
            // cập nhật lại số lượng các OrderDetail trong các cart của khách hàng khác có
            // còn hợp lệ không
            List<Bill> allCart = BillDB.getInstance().selectAll()
                        .stream().filter(b -> "Storing".equals(b.getStatusOrder().toString()))
                     .collect(Collectors.toList()); 
            allCart.forEach(c -> c.getOrderDetails()
                    .forEach(o -> 
                    {
                        // nếu số lượng = 0, xóa orderDetail này
                        if(o.getBook().getStocks() == 0)
                            cart.getOrderDetails().remove(o);
                        // nếu số lương > số lượng trong kho, thì set về số lượng trong kho
                        else if(o.getQuantity() > o.getBook().getStocks())
                            o.setQuantity(o.getBook().getStocks());
                        // lưu thay đổi orderDetail
                        emFinal.merge(o);
                    }));

            tr.commit();
            return true;
        }
        catch(Exception ex){
            if(tr != null && tr.isActive())
                tr.rollback();
            ex.printStackTrace();
            return false;
        }
        finally{
            if(em != null)
                em.close();
        }
    }
    public boolean setDefaltAddress(Customer customer, Address a){
        EntityManager em = null;
        EntityTransaction tr = null;

        try {
            em = DBUtil.getEmFactory().createEntityManager();
            tr = em.getTransaction();
            tr.begin(); 

            if (a != null) {
                final EntityManager emFinal = em;
                // Đặt tất cả các địa chỉ mặc định thành false
                customer.getAddresses().stream()
                    .filter(b -> b.isDefaultAddress())
                    .forEach(b -> {
                        b.setDefaultAddress(false);
                        emFinal.merge(b);  
                    });

                a.setDefaultAddress(true);
                em.merge(a);

                tr.commit(); // Commit giao dịch
                return true;
            } else {
                System.out.println("Lỗi tìm địa chỉ");
                tr.rollback(); 
                return false;
            }
        } catch (Exception ex) {
            if (tr != null && tr.isActive()) {
                tr.rollback(); // Rollback khi có ngoại lệ xảy ra
            }
            ex.printStackTrace();
            return false;
        } finally {
            if (em != null) {
                em.close(); // Đóng EntityManager khi xong
            }
        }
    }
    public boolean insertCustomer(Customer c){
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
            return true;
        }
    }
    public boolean deleteCustomer(int id){
        try(EntityManager em = DBUtil.getEmFactory().createEntityManager()){
            Customer customer = em.find(Customer.class, id);
            em.getTransaction().begin();
            em.remove(customer);
            em.getTransaction().commit();
            return true;
        }
        catch (TransientObjectException ex) {
            return false;
        }
    }
}
