package casestudy.location;

import annotation.DomainService;

/**
 * @author liwenjun
 * @ClassName LocationService
 * @Date 2019-12-11 14:22
 */
@DomainService
public abstract class LocationService {
    public abstract void resolveByPortCode(String portCode);
}
