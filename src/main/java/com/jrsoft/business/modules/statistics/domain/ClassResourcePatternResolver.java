package com.jrsoft.business.modules.statistics.domain;/**
 * Created by chesijian on 2021/4/19.
 */
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @ClassName ClassResourcePatternResolver
 * @Description TODO
 * @Author chesijian
 * @Date 2021/4/19 14:02
 * @Version 1.0
 */
public class ClassResourcePatternResolver implements ResourceLoaderAware {

    private static ResourceLoader resourceLoader;

    public static Set<String> findAllClassPathResources() throws IOException {
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        MetadataReaderFactory metaReader = new CachingMetadataReaderFactory(resourceLoader);
        // 通过classpath
//        Resource[] resources = resolver.getResources("classpath*:com/jxz/java/demo/command/*.class");
        // 通过file
//        Resource[] resources = resolver.getResources("file:/F:/flink-demo/target/classes/com/jxz/java/demo/command/*.class");
        String classResourcesPath = getClassResourcesPath();
        Resource[] resources = resolver.getResources(classResourcesPath);
        Set<String> set = new LinkedHashSet<>(16);
        for (Resource r : resources) {
            MetadataReader reader = metaReader.getMetadataReader(r);
            String className = reader.getClassMetadata().getClassName();
            set.add(className);
        }
        return set;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    // 获取上一层export包目录
    private static String getClassResourcesPath() {
        URL url = ClassResourcePatternResolver.class.getResource("../");
        String protocol = url.getProtocol();
        String path = url.getPath();
        return protocol + ":" + path + "export/*.class";
    }
}
