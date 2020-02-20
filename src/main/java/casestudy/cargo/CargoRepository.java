package casestudy.cargo;

import annotation.Repository;

/**
 * @author liwenjun
 * @ClassName CargoRepository
 * @Date 2019-12-11 13:51
 */
@Repository
public class CargoRepository {
    public Cargo findByTackingID(String id) {
        return new Cargo();
    }
    public Cargo findByCustomerID(String id) {
        return new Cargo();
    }

}
