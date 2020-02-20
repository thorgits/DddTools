package code2modle.scanpackage.stereotype;

import annotation.*;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author liwenjun
 * @ClassName MetaSet
 * @Date 2019-12-13 14:17
 */
public class MetaSet {

    Map<Class, Set<Meta>> metaMap = new HashMap<>();

    public Map<Class, Set<Meta>> getMetaMap() {
        return metaMap;
    }

    public void setMetaMap(Map<Class, Set<Meta>> metaMap) {
        this.metaMap = metaMap;
    }

    Class[] clazz = {AggregatePart.class, AggregateRoot.class, DomainService.class,
            Entity.class, Event.class, Repository.class, Specification.class, ValueObject.class};

    public void put(Field field,Class targetClz, Boolean odd) {
        for (Class clz : clazz) {
            if (targetClz.getAnnotation(clz) != null) {
                if (metaMap.get(clz) != null) {
                    metaMap.get(clz).add(new Meta(field,targetClz,odd));
                } else {
                    Set<Meta> temp = new HashSet<>();
                    temp.add(new Meta(field,targetClz,odd));
                    metaMap.put(clz, temp);
                }
            }

        }
    }

    public void remove(Class<?> targetClz) {
        Set<Meta> temp;
        if ((temp = metaMap.getOrDefault(targetClz, null)) == null) {
            new UnsupportedOperationException("不存在该实体");
        } else {
            temp.remove(targetClz);
        }

    }

}