package code2modle.scanpackage.stereotype;


import java.lang.reflect.Field;

/**
 * @author liwenjun
 * @ClassName Meta
 * @Date 2019-12-17 16:42
 */
public class Meta {
    Field field;
    Class metaClazz;
    Boolean odd;
    Integer level;

    public Meta(Field field, Class metaClazz, Boolean odd) {
        this.field = field;
        this.metaClazz = metaClazz;
        this.odd = odd;
    }

    public Meta(Class metaClazz, Integer level) {
        this.metaClazz = metaClazz;
        this.level = level;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Class getMetaClazz() {
        return metaClazz;
    }

    public void setMetaClazz(Class metaClazz) {
        this.metaClazz = metaClazz;
    }

    public Boolean getOdd() {
        return odd;
    }

    public void setOdd(Boolean odd) {
        this.odd = odd;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
