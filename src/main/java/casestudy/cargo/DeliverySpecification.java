package casestudy.cargo;

import annotation.AggregatePart;
import annotation.Comment;
import annotation.ValidatesSpec;
import annotation.ValueObject;
import casestudy.shared.LocationShared;
import casestudy.location.LocationService;

import java.util.Date;

/**
 * @author liwenjun
 * @ClassName DeliverySpecification
 * @Date 2019-12-11 13:59
 */
@ValueObject(root = Cargo.class)
public class DeliverySpecification {
    public  Date arrivalTime;
    @Comment(note = "destination")
    LocationShared locationShared;
    LocationService locationService;
    @ValidatesSpec
    public  boolean verify(LocationShared  locationShared){
        return false;
    }

}
