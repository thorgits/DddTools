package casestudy.cargo;

import annotation.DefinesIdentity;
import annotation.Entity;

/**
 * @author liwenjun
 * @ClassName DeliveryHistory
 * @Date 2019-12-11 14:06
 */
@Entity
public class DeliveryHistory {
    @DefinesIdentity
     String id;
    HandlingEvent[] events;
}
