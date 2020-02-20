package casestudy.shared;

        import annotation.ValueObject;
        import casestudy.location.Location;

/**
 * @author liwenjun
 * @ClassName LocationShared
 * @Date 2019-12-11 14:11
 */
@ValueObject
public class LocationShared {
    String portCode;
    Location location;
}
