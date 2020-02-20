package code2modle.scanpackage;

import code2modle.scanpackage.stereotype.Element;
import code2modle.scanpackage.stereotype.MetaSet;


import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author liwenjun
 * @ClassName ElementFactory
 * @Date 2019-12-12 13:48
 */
public class ElementFactory {
    public static HashMap<Class, Element> elementHub = new HashMap<>();

    public static HashMap<Class, Element> getElementHub() {
        return elementHub;
    }

    public static void setElementHub(HashMap<Class, Element> elementHub) {
        ElementFactory.elementHub = elementHub;
    }

    public static void newElement(Class clazz) {
        if (!elementHub.containsKey(clazz)) {
            Element element = new Element();
            elementHub.put(clazz, element);
        } else {
            Element e = elementHub.get(clazz);
            e.setMethods(clazz.getDeclaredMethods());
            e.setFullyQualifiedName(clazz.getName());
            e.setOralClazz(clazz);
            Field[] fields = clazz.getDeclaredFields();
            e.setFields(fields);
            MetaSet metaSet = new MetaSet();
            for (Field f : fields) {
                Class fieldClazz = f.getType();
                if (isPrimitiveData(fieldClazz)) continue;
                if (Collection.class.isAssignableFrom(f.getType())) {
                    Type fc = f.getGenericType(); // 关键的地方，如果是List类型，得到其Generic的类型
                    if (fc instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType) fc;
                        Class genericClazz = (Class) pt.getActualTypeArguments()[0];
                        if (isPrimitiveData(genericClazz)) {
                            continue;
                        } else {
                            metaSet.put(f,genericClazz,true);
                        }
                    }

                } else if (f.getType().isArray()) {
                    Class componentType = f.getType().getComponentType();
                    if (isPrimitiveData(componentType)) {
                        continue;
                    } else {
                        metaSet.put(f,componentType,true);
                    }
                }else {
                    metaSet.put(f,f.getType(),false);
                }
            }
            e.setMetaSet(metaSet);
        }
    }

    public static boolean isPrimitiveData(Class fieldClazz) {
        if (fieldClazz.isPrimitive() || fieldClazz.getName().startsWith("java.lang")
                || fieldClazz.isInstance(Date.class) || Map.class.isAssignableFrom(fieldClazz)) {
            return true;
        } else {
            return false;
        }
    }
}
