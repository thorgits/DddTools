package code2modle.scanpackage.script;

import code2modle.scanpackage.ElementFactory;
import code2modle.scanpackage.ModifierUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liwenjun
 * @ClassName Item
 * @Date 2019-12-17 17:28
 */
public class Item {
    String name;
    Field[] fields;
    Method[] methods;
    List<Annotation> annotations;
    StringBuilder itemStr ;

    public String createItem(Class c) {
        itemStr= new StringBuilder();
        if (c.isInterface()) {
            itemStr.append(" interface ");
        } else if (Modifier.isAbstract(c.getModifiers())) {
            itemStr.append("abstract class ");
        } else {
            itemStr.append(" class ");
        }
        name = c.getName();
        itemStr.append(name);
        annotations = ElementFactory.getElementHub().get(c).getAnnotations();
        itemStr.append("<<");
        for (int i = 0; i < annotations.size() - 1; i++) {
            itemStr.append(annotations.get(i).annotationType().getSimpleName() + ", ");
        }
        itemStr.append(annotations.get(annotations.size() - 1).annotationType().getSimpleName());
        itemStr.append(">> { \n");
        fields = ElementFactory.getElementHub().get(c).getFields();
        for (Field f : fields) {
            itemStr.append(ModifierUtil.getModifier(Modifier.toString(f.getModifiers())));
            itemStr.append(f.getType().getSimpleName() + " " + f.getName() + "\n");
        }
        methods = ElementFactory.getElementHub().get(c).getMethods();
        for (Method e : methods) {
            itemStr.append(ModifierUtil.getModifier(Modifier.toString(e.getModifiers())));
            itemStr.append(e.getName() + "(");
            Class<?>[] parameterizedTypes = e.getParameterTypes();
            for (int i = 0; i < parameterizedTypes.length - 1; i++) {
                itemStr.append(parameterizedTypes[i].getSimpleName() + " ,");
            }
            itemStr.append(parameterizedTypes[parameterizedTypes.length - 1].getSimpleName());
            itemStr.append(" ) \n");

        }
        itemStr.append("}");


        return itemStr.toString();

    }

    public String createItemSketch(Class c) {
        itemStr= new StringBuilder();
         itemStr.append(" class ");
        name = c.getSimpleName();
        itemStr.append(name);
        annotations = ElementFactory.getElementHub().get(c).getAnnotations();
        itemStr.append(ModifierUtil.getStereotype(annotations.get(0).annotationType().getSimpleName()));
        itemStr.append("\n");
        itemStr.append("hide "+name+" members\n");
        return itemStr.toString();

    }


}
