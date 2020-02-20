package casestudy.cargo;

import annotation.Comment;
import annotation.AggregateRoot;
import annotation.DefinesIdentity;
import annotation.Entity;
import casestudy.shared.CustomerShared;
import casestudy.customer.Customer;


/**
 * @author liwenjun
 * @ClassName Cargo
 * @Date 2019-12-11 13:56
 */
@AggregateRoot
@Entity
public class Cargo {
    @DefinesIdentity
    private  String trackingID;
    private Customer[] customers;
    public DeliveryHistory deliveryHistory;
    @Comment(note = "goal")
    private DeliverySpecification deliverySpecification;
    CustomerShared customerShared;
}
