package code2modle.scanpackage;


import code2modle.scanpackage.stereotype.Element;
import code2modle.scanpackage.stereotype.MetaEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;


public class ClasspathPackageScanner {
    private Logger logger = LoggerFactory.getLogger(ClasspathPackageScanner.class);

    private String basePackage;
    private ClassLoader cl;


    public ClasspathPackageScanner(String basePackage) {
        this.basePackage = basePackage;
        this.cl = getClass().getClassLoader();

    }


    public ClasspathPackageScanner(String basePackage, ClassLoader cl) {
        this.basePackage = basePackage;
        this.cl = cl;
    }

    /**
     * Get all fully qualified names located in the specified package
     * and its sub-package.
     *
     * @return A list of fully qualified names.
     * @throws IOException
     */
    public List<Class> getFullyQualifiedClassNameList() throws IOException {
        logger.debug("开始扫描包{}下的所有类", basePackage);

        return doScan(basePackage, new ArrayList<>());
    }

    /**
     * Actually perform the scanning procedure.
     *
     * @param basePackage
     * @param nameList    A list to contain the result.
     * @return A list of fully qualified names.
     * @throws IOException
     */
    private List<Class> doScan(String basePackage, List<Class> nameList) throws IOException {
        // replace dots with splashes
        String splashPath = StringUtil.dotToSplash(basePackage);

        // get file path
        URL url = cl.getResource(splashPath);
        String filePath = StringUtil.getRootPath(url);

        // Get classes in that package.
        // If the web server unzips the jar file, then the classes will exist in the form of
        // normal file in the directory.
        // If the web server does not unzip the jar file, then classes will exist in jar file.
        List<String> names = null; // contains the name of the class file. e.g., Apple.class will be stored as "Apple"
        if (isJarFile(filePath)) {
            // jar file
            if (logger.isDebugEnabled()) {
                logger.debug("{} 是一个JAR包", filePath);
            }

            names = readFromJarFile(filePath, splashPath);
        } else {
            // directory
            if (logger.isDebugEnabled()) {
                logger.debug("{} 是一个目录", filePath);
            }

            names = readFromDirectory(filePath);
        }

        for (String name : names) {
            if (isClassFile(name)) {
                //nameList.add(basePackage + "." + StringUtil.trimExtension(name));
                String fullName = toFullyQualifiedName(name, basePackage);
                Class clazz = null;
                try {
                    clazz = Class.forName(fullName);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
               Annotation[] annotations = clazz.getAnnotations();
                Integer code = 0;
                Element element = new Element();
                for (int i = 0; i < annotations.length; i++) {
                    String annotationType = annotations[i].annotationType().getSimpleName();
                    if (MetaEnum.getvalueOf(annotationType).getCode() != null) {
                        element.getAnnotations().add(annotations[i]);
                        code |= MetaEnum.getvalueOf(annotationType).getCode();
                    }
                    ;
                    logger.debug("{} 对应的注解 {}", clazz.getName(), annotations[i].annotationType().getSimpleName());
                }
                if (code != 0 && element != null) {
                    element.setAnnotationCode(code);
                    ElementFactory.getElementHub().put(clazz, element);
                    nameList.add(clazz);
                }
            } else {
                // this is a directory
                // check this directory for more classes
                // do recursive invocation
                doScan(basePackage + "." + name, nameList);
            }
        }

        if (logger.isDebugEnabled()) {
            for (Class n : nameList) {
                logger.debug("找到{}", n);
                ElementFactory.newElement(n);
            }
        }

        return nameList;
    }

    /**
     * Convert short class name to fully qualified name.
     * e.g., String -> java.lang.String
     */
    private String toFullyQualifiedName(String shortName, String basePackage) {
        StringBuilder sb = new StringBuilder(basePackage);
        sb.append('.');
        sb.append(StringUtil.trimExtension(shortName));

        return sb.toString();
    }

    private List<String> readFromJarFile(String jarPath, String splashedPackageName) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.debug("从JAR包中读取类: {}", jarPath);
        }

        JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
        JarEntry entry = jarIn.getNextJarEntry();

        List<String> nameList = new ArrayList<>();
        while (null != entry) {
            String name = entry.getName();
            if (name.startsWith(splashedPackageName) && isClassFile(name)) {
                nameList.add(name);
            }

            entry = jarIn.getNextJarEntry();
        }

        return nameList;
    }

    private List<String> readFromDirectory(String path) {
        File file = new File(path);
        String[] names = file.list();

        if (null == names) {
            return null;
        }

        return Arrays.asList(names);
    }

    private boolean isClassFile(String name) {
        return name.endsWith(".class");
    }

    private boolean isJarFile(String name) {
        return name.endsWith(".jar");
    }



}