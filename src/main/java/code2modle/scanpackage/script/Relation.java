package code2modle.scanpackage.script;

import annotation.Comment;
import code2modle.scanpackage.ElementFactory;
import code2modle.scanpackage.stereotype.Meta;
import code2modle.scanpackage.stereotype.MetaSet;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author liwenjun
 * @ClassName Relation
 * @Date 2019-12-17 17:35
 */
public class Relation {
    StringBuilder relationStr;

    public String createRelation(Class c) {
        relationStr = new StringBuilder();
        MetaSet e = ElementFactory.getElementHub().get(c).getMetaSet();
        Map<Class, Set<Meta>> metaMap = e.getMetaMap();
        Iterator iterator = metaMap.keySet().iterator();
        while (iterator.hasNext()) {
            Class index = (Class) iterator.next();
            Set<Meta> metas = metaMap.get(index);
            for (Meta m : metas) {
                StringBuilder des=new StringBuilder();
               StringBuilder tmpStr=new StringBuilder();
                tmpStr.append(c.getName() + " --> ");
                if (m.getOdd()) {
                    tmpStr.append("\"*\" " + m.getMetaClazz().getName());
                } else {
                    tmpStr.append(m.getMetaClazz().getName());
                }
                if (m.getField().getAnnotation(Comment.class)!=null){
                    des.append(m.getField().getAnnotation(Comment.class).note()+" ");
                    des.append(m.getField().getAnnotation(Comment.class).constraint());
                }
                if (!des.toString().isEmpty()){
                    tmpStr.append(":"+des.toString() + "\n");
                }else {
                    tmpStr.append("\n") ;
                }
              if (relationStr.indexOf(tmpStr.toString())==-1){
                  relationStr.append(tmpStr);
              }
            }

        }

        return relationStr.toString();
    }
    public String createRelationSketch(Class c) {
        relationStr = new StringBuilder();
        MetaSet e = ElementFactory.getElementHub().get(c).getMetaSet();
        Map<Class, Set<Meta>> metaMap = e.getMetaMap();
        Iterator iterator = metaMap.keySet().iterator();
        while (iterator.hasNext()) {
            Class index = (Class) iterator.next();
            Set<Meta> metas = metaMap.get(index);
            for (Meta m : metas) {
                StringBuilder tmpStr=new StringBuilder();
                tmpStr.append(c.getSimpleName() + " --> ");
                tmpStr.append(m.getMetaClazz().getSimpleName());
                tmpStr.append("\n") ;
                if (relationStr.indexOf(tmpStr.toString())==-1){
                    relationStr.append(tmpStr);
                }
            }
        }
        return relationStr.toString();
    }

}
