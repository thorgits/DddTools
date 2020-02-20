package code2modle.scanpackage;

import annotation.AggregateRoot;
import casestudy.cargo.Cargo;
import code2modle.scanpackage.script.*;
import code2modle.scanpackage.stereotype.Element;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author liwenjun
 * @ClassName PlantUMLGenerator
 * @Date 2019-12-17 17:24
 */
public class PlantUMLGenerator {
    public static void main(String[] args) throws IOException {
        scanPackage("/Users/liwenjun/IdeaProjects/plantUMLDemo/src/main/resources","casestudy","cargo");
    }

    public static void scanPackage(String path,String packageName,String projectName) throws IOException {
        ClasspathPackageScanner scan = new ClasspathPackageScanner(packageName);
        scan.getFullyQualifiedClassNameList();
        VerifyConstraints verifyConstraints=new VerifyConstraints();
        verifyConstraints.verify(projectName.toUpperCase(),path+"/markdown/"+projectName+".md");
        Script modelScript = generateModel();
        writeModel(modelScript,path+"/project/"+projectName+".puml");
        Script sequenceScript=generateSequence();
        writeSequence(sequenceScript,path+"/quality/"+projectName+".puml");
        Script sketchScript =generateSketch();
        writeSketch(sketchScript,path+"/sketch/"+projectName+".puml");
    }

    public static Script generateModel() {
        HashMap<Class, Element> elementHashMap = ElementFactory.getElementHub();
        Set<Class> elementSet = elementHashMap.keySet();
        Script script = new Script();
        for (Class c : elementSet) {
            Item item = new Item();
            script.getItems().add(item.createItem(c));
            Relation relation = new Relation();
            String rel = relation.createRelation(c);
            if (!rel.isEmpty()) {
                script.getRelations().add(rel);
            }
        }
        script.setHeader(new Header("@startuml"));
        script.setStyle(new Style("!includeurl https://raw.githubusercontent.com/xuanye/plantuml-style-c4/master/core.puml\n" +
                "skinparam linetype ortho\n" +
                "left to right direction\n" +
                "GREY_ARROW"));
        script.setFooter(new Footer("@enduml"));
        return script;
    }

    public static void writeModel(Script script,String project) {

        File path = new File(project);

        BufferedWriter out = null;
        try {
            File fileParent = path.getParentFile();
            if (!fileParent.exists()){
                fileParent.mkdirs();
            }
            path.createNewFile();
            out = new BufferedWriter(new FileWriter(path, false));
            out.write(script.getHeader().getHeader());
            out.write("\r\n");
            out.write(script.getStyle().getStyleStr());
            out.write("\r\n");
            out.write("\'!@Item \r\n");
            List<String> items = script.getItems();
            for (String item : items) {
                out.write(item);
                out.write("\r\n");
            }
            out.write("\'!@Relation \r\n");
            List<String> relations = script.getRelations();
            for (String relation : relations) {
                out.write(relation);
                out.write("\r\n");
            }
            out.write("\r\n");
            out.write(script.getFooter().getFooter());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Script generateSequence() {
        HashMap<Class, Element> elementHashMap = ElementFactory.getElementHub();
        Set<Class> keySet=elementHashMap.keySet();
        Script script=new Script();
        for (Class c:keySet) {
            if (c.getAnnotation(AggregateRoot.class)!=null){
                Sequence sequence=new Sequence();
                script.getSequences().add(sequence.createInvoke(Cargo.class,new HashSet<>(),new HashSet<>()));
            }
        }
        script.setHeader(new Header("@startuml"));
        script.setStyle(new Style("!includeurl https://raw.githubusercontent.com/xuanye/plantuml-style-c4/master/core.puml\n" +
                "top to bottom direction \n" +
                "GREY_ARROW"));
        script.setFooter(new Footer("@enduml"));
        return script;
    }

    public static void writeSequence(Script script,String project) {

        File path = new File(project);

        BufferedWriter out = null;
        try {
            File fileParent = path.getParentFile();
            if (!fileParent.exists()){
                fileParent.mkdirs();
            }
            path.createNewFile();
            out = new BufferedWriter(new FileWriter(path, false));
            out.write(script.getHeader().getHeader());
            out.write("\r\n");
            out.write(script.getStyle().getStyleStr());
            out.write("\r\n");
            out.write("\'!@Sequence \r\n");
            List<String> sequences = script.getSequences();
            for (String sequence : sequences) {
                out.write(sequence);
                out.write("\r\n");
            }
            out.write(script.getFooter().getFooter());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Script generateSketch() {
        HashMap<Class, Element> elementHashMap = ElementFactory.getElementHub();
        Set<Class> elementSet = elementHashMap.keySet();
        Script script = new Script();
        for (Class c : elementSet) {
            Item item = new Item();
            script.getItems().add(item.createItemSketch(c));
            Relation relation = new Relation();
            String rel = relation.createRelationSketch(c);
            if (!rel.isEmpty()) {
                script.getRelations().add(rel);
            }
        }
        script.setHeader(new Header("@startuml"));
        script.setStyle(new Style("left to right direction\n" +
                "!includeurl https://raw.githubusercontent.com/xuanye/plantuml-style-c4/master/core.puml\n" +
                "skinparam rectangle {\n" +
                " roundCorner 100\n" +
                "}"));
        script.setFooter(new Footer("@enduml"));
        return script;
    }



    public static void writeSketch(Script script,String project) {
        File path = new File(project);
        BufferedWriter out = null;
        try {
            File fileParent = path.getParentFile();
            if (!fileParent.exists()){
                fileParent.mkdirs();
            }
            path.createNewFile();
            out = new BufferedWriter(new FileWriter(path, false));
            out.write(script.getHeader().getHeader());
            out.write("\r\n");
            out.write(script.getStyle().getStyleStr());
            out.write("\r\n");
            out.write("\'!@Sketch \r\n");
            out.write("package Cargo <<Rectangle>> #8B9BAC{\n");
            List<String> items = script.getItems();
            for (String item : items) {
                out.write(item);
            }
            out.write("\'!@Relation \r\n");
            List<String> relations = script.getRelations();
            for (String relation : relations) {
                out.write(relation);
            }
            out.write("}");
            out.write("\r\n");
            out.write(script.getFooter().getFooter());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

