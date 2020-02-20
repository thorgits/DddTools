package code2modle.scanpackage;


import annotation.AggregatePart;
import annotation.AggregateRoot;
import annotation.DefinesIdentity;
import annotation.ValidatesSpec;
import code2modle.scanpackage.stereotype.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author liwenjun
 * @ClassName VerifyConstraints
 * @Date 2020-01-05 17:52
 */
public class VerifyConstraints {
    HashMap<Class, Element> elementHashMap = ElementFactory.getElementHub();
    List<ConstraintException> constraintExceptions = new LinkedList<>();
    int aggregateRoot=0,aggregatePart=0,repository=0,entity=0,valueObject=0,service=0,spec=0,event=0;
    public void verify(String project,String location) {
        StringBuilder str=new StringBuilder();

        str.append("# "+project +" Quality Report\n" +
                "###1.Stereotype statistic\n" +
                "| Stereotypes | AggregateRoot | AggregatePart | Repository | Entity | ValueObjet | Service | Specification|  Event   |\n" +
                "| ----------- | ------------  | ------------- | ---------- | ------ | ---------- | ------- |  ----------  | -------- |\n" +
                "| Quantity    |");

        Set<Class> elementSet = elementHashMap.keySet();
        for (Class c : elementSet) {
            check(elementHashMap.get(c));
        }
        str.append(aggregateRoot+"|"+aggregatePart+"|"+repository+"|"+entity+"|"+valueObject+"|"+service+"|"+spec+"|"+event+"|\n");
        str.append("###2.Violate Constraints\n" +
                "|Error code|Class|Rule|\n" +
                "|----------|-----|----|\n");
        for (ConstraintException c :constraintExceptions) {
            str.append("|"+c.getConstraint().getCode()+"|");
            str.append(c.getMessage()+"|");
            str.append("violate "+c.getConstraint().getRule()+"|");
            str.append("\n");
        }
        str.append("###3.Circle Dependency \n" +
                "![image](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/thorgits/Image/master/quality/cargo.puml)\n");
        printMd(str,location);
    }
    public void printMd(StringBuilder str,String location){
        File path = new File(location);
        BufferedWriter out = null;
        try {
            File fileParent = path.getParentFile();
            if (!fileParent.exists()){
                fileParent.mkdirs();
            }
            path.createNewFile();
            out = new BufferedWriter(new FileWriter(path, false));
            out.write(str.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void check(Element e) {
        ArrayList<Annotation> annotations = e.getAnnotations();
        try {
            for (Annotation a : annotations) {
                switch (a.annotationType().getSimpleName()) {
                    case "ValueObject":
                        valueObject++;
                        valueObjectCheck(e);
                        break;
                    case "Entity":
                        entity++;
                        entityCheck(e);
                        break;
                    case "Event":
                        event++;
                        eventCheck(e);
                        break;
                    case "Specification":
                        spec++;
                        specCheck(e);
                        break;
                    case "Repository":
                        repository++;
                        repositoryCheck(e);
                        break;
                    case "AggregatePart":
                        aggregatePart++;
                        aggregatePartCheck(e);
                        break;
                    case "AggregateRoot":
                        aggregateRoot++;
                        aggregateRootCheck(e);
                        break;
                    case "DomainService":
                        service++;
                        serviceCheck(e);
                        break;
                    default:
                        break;

                }
                definesIdentityCheck(e);
                validateSpecCheck(e);
            }
        } catch (ConstraintException constraintE) {
            constraintExceptions.add(constraintE);
        }

    }

    public void aggregatePartCheck(Element e) throws ConstraintException {
        if ((e.getAnnotationCode() & MetaEnum.valueOf("Entity").getCode()) != 0 ||
                (e.getAnnotationCode() & MetaEnum.valueOf("ValueObject").getCode()) != 0) {
        } else {
            throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_01);
        }
        if (e.getOralClazz().getDeclaredAnnotation(AggregatePart.class).root() == null) {
            throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_02);
        }
        Set<Meta> metas = e.getMetaSet().getMetaMap().get(AggregateRoot.class);
        if (metas != null) {
            for (Meta m : metas) {
                if (m.getMetaClazz() != e.getOralClazz().getDeclaredAnnotation(AggregatePart.class).root()) {
                    throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_03);
                }
            }
        }
        // DDD要求一个项目属于同一个Context Constraint.CONSTRAINT_04
    }

    public void aggregateRootCheck(Element e) throws ConstraintException {
        if ((e.getAnnotationCode() & MetaEnum.ENTITY.getCode()) != 0) {
        } else {
            throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_05);
        }
        if (e.getMetaSet().getMetaMap().get(AggregatePart.class)==null) {
            throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_06);

        }
    }

    public void entityCheck(Element e) throws ConstraintException {
        Method[] methods = e.getMethods();
        Field[] fields = e.getFields();
        boolean check = false;
        for (Method m : methods) {
            if (m.getDeclaredAnnotation(DefinesIdentity.class) != null) {
                check = true;
            }
        }
        for (Field f : fields) {
            if (f.getDeclaredAnnotation(DefinesIdentity.class) != null) {
                check = true;
            }
        }
        if (!check) {
            throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_07);
        }

    }

    public void valueObjectCheck(Element e) {

    }

    public void eventCheck(Element e) {

    }

    public void repositoryCheck(Element e) throws ConstraintException {
        if (!e.getAnnotationCode().equals(MetaEnum.REPOSITORY.getCode())) {
            throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_08);
        }
        if (e.getOralClazz().getDeclaredMethods().length >= 1 || e.getOralClazz().getDeclaredFields().length >= 1) {
        } else {
            throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_09);
        }
        // Repository  是否包含Aggregate CONSTRAINT_10
    }

    public void serviceCheck(Element e) throws ConstraintException {
            if (!e.getAnnotationCode().equals(MetaEnum.DOMAINSERVICE.getCode()) ) {
            throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_11);
        }
        if (e.getOralClazz().getDeclaredMethods().length >= 1 || e.getOralClazz().getDeclaredFields().length == 0) {
        } else {
            throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_12);
        }

    }

    public void specCheck(Element e) throws ConstraintException {
        if (!e.getAnnotationCode().equals(MetaEnum.SPECIFICATION.getCode())){
            throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_13);
        }
        Method[] methods = e.getMethods();
        boolean check = false;
        for (Method m : methods) {
            if (m.getDeclaredAnnotation(ValidatesSpec.class) != null) {
                if (m.getParameterTypes().length < 1) {
                    throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_16);
                }
                check = true;
            }
        }
        if (!check) {
            throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_14);
        }
        if (e.getMetaSet().getMetaMap().size() <= 0) {
            throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_15);
        }

    }

    public void closureCheck(Element e) throws ConstraintException {
        //为定义注解类图中并不适用
    }

    public void definesIdentityCheck(Element e) throws ConstraintException {
        Method[] methods = e.getMethods();
        for (Method m : methods) {
            if (m.getDeclaredAnnotation(DefinesIdentity.class) != null) {
                if (m.getDeclaredAnnotation(ValidatesSpec.class)!=null){
                    throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_19);
                }
                if ((e.getAnnotationCode() & MetaEnum.VALUEOBJECT.getCode()) == 0) {
                    throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_20);
                }
            }
        }

    }

    public void sideEffectFreeCheck(Element e) {
        //

    }

    public void validateSpecCheck(Element e) throws ConstraintException{
        Method[] methods = e.getMethods();
        for (Method m : methods) {
            if (m.getDeclaredAnnotation(ValidatesSpec.class) != null) {
                if (m.getReturnType()!=boolean.class || m.getReturnType()!=Boolean.class){
                    throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_22);
                }
                if ((e.getAnnotationCode() & MetaEnum.SPECIFICATION.getCode()) == 0) {
                    throw new ConstraintException(e.getFullyQualifiedName(), Constraint.CONSTRAINT_23);
                }
            }
        }
    }

    public void boundedContextCheck(Element e) {
        //

    }
}



