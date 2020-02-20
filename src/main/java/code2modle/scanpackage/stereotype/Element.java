package code2modle.scanpackage.stereotype;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * @author liwenjun
 * @ClassName Element
 * @Date 2019-12-12 13:03
 */

public class Element {
    ArrayList<Annotation> annotations=new ArrayList<>();
    Integer annotationCode;
    String fullyQualifiedName;
    Class<?> oralClazz;
    Field[]  fields;
    Method[] methods;
    Exception[] exceptions;
    MetaSet metaSet;

    public Integer getAnnotationCode() {
        return annotationCode;
    }

    public Class<?> getOralClazz() {
        return oralClazz;
    }

    public void setOralClazz(Class<?> oralClazz) {
        this.oralClazz = oralClazz;
    }

    public void setAnnotationCode(Integer annotationCode) {
        this.annotationCode = annotationCode;
    }

    public ArrayList<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(ArrayList<Annotation> annotations) {
        this.annotations = annotations;
    }

    public String getFullyQualifiedName() {
        return fullyQualifiedName;
    }

    public void setFullyQualifiedName(String fullyQualifiedName) {
        this.fullyQualifiedName = fullyQualifiedName;
    }

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    public Method[] getMethods() {
        return methods;
    }

    public void setMethods(Method[] methods) {
        this.methods = methods;
    }

    public Exception[] getExceptions() {
        return exceptions;
    }

    public void setExceptions(Exception[] exceptions) {
        this.exceptions = exceptions;
    }

    public MetaSet getMetaSet() {
        return metaSet;
    }

    public void setMetaSet(MetaSet metaSet) {
        this.metaSet = metaSet;
    }
}
