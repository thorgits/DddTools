package annotation;




import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
/**
 * @author liwenjun
 * @ClassName Entity
 * @Date 2019-12-11 12:50
 */
public @interface Entity {


}
