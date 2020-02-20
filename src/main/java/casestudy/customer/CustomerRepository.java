package casestudy.customer;

import annotation.Repository;

/**
 * @author liwenjun
 * @ClassName CustomerRepository
 * @Date 2019-12-11 14:25
 */
@Repository
public interface CustomerRepository {
    Customer findByName(String name);
    Customer findByCustomerID(String ID);
}
