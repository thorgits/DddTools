package code2modle.scanpackage.script;

import code2modle.scanpackage.ElementFactory;
import code2modle.scanpackage.stereotype.Meta;
import code2modle.scanpackage.stereotype.MetaSet;

import java.util.*;

/**
 * @author liwenjun
 * @ClassName Sequence
 * @Date 2019-12-19 20:44
 */
public class Sequence {
    StringBuilder stringBuilder=new StringBuilder();
    public String createInvoke(Class c, Set<Integer> setMark, Set<Integer> setBack) {
        if (setBack.contains(c.hashCode())) return stringBuilder.toString();
        MetaSet e = ElementFactory.getElementHub().get(c).getMetaSet();
        setMark.add(c.hashCode());
        Map<Class, Set<Meta>> metaMap = e.getMetaMap();
        Set<Class> elementSet = metaMap.keySet();
        HashSet<Class> classHashSet = new HashSet<>();
        for (Class cl : elementSet) {
            Set<Meta> metas = metaMap.get(cl);
            for (Meta m : metas) {
                if (classHashSet.contains(m.getMetaClazz())) continue;
                classHashSet.add(m.getMetaClazz());
                if (setMark.contains(m.getMetaClazz().hashCode())) {
                    String tm = "("+c.getSimpleName() +")"+ " .[#red].> " +"("+ m.getMetaClazz().getSimpleName()+")"+":"+"circular"+"\n";
                    stringBuilder.append(tm);
                    System.out.println(tm);
                } else {
                    setMark.add(m.getMetaClazz().hashCode());
                    String tm2= "("+c.getSimpleName()+")" + " --> " +"("+ m.getMetaClazz().getSimpleName()+")\n";
                    System.out.println(tm2);
                    stringBuilder.append(tm2);
                    createInvoke(m.getMetaClazz(), setMark, setBack);
                    setMark.remove(m.getMetaClazz().hashCode());
                }
                setBack.add(m.getMetaClazz().hashCode());
            }
        }
        return stringBuilder.toString();
    }
}
