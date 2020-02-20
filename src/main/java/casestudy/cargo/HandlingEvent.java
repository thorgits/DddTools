package casestudy.cargo;

import annotation.Comment;
import annotation.DefinesIdentity;
import annotation.Entity;

import java.util.Date;

/**
 * @author liwenjun
 * @ClassName HandlingEvent
 * @Date 2019-12-11 14:07
 */
@Entity
public class HandlingEvent {
    private Date completeTime;
    @DefinesIdentity
    private String type;
    public CarrierMovement carrierMovement;
    @Comment(note = "handled")
    private Cargo[] cargo;
}
