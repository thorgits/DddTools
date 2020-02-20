package casestudy.customer;

import annotation.DefinesIdentity;
import annotation.Entity;

/**
 * @author liwenjun
 * @ClassName Customer
 * @Date 2019-12-11 14:24
 */
@Entity
public class Customer {
    public String name;
    @DefinesIdentity
    public String customerID;
}
