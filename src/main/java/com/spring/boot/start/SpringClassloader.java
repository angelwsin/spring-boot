package com.spring.boot.start;

import org.springframework.boot.loader.JarLauncher;
import org.springframework.boot.loader.archive.Archive;
import org.springframework.boot.loader.archive.JarFileArchive;

import java.io.File;
import java.net.URI;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class SpringClassloader {


    public static void main(String[] args) throws  Exception{


        //spring-boot-loader 模块 自定义了  LaunchedURLClassLoader 类加载器

        //JarLauncher.main(args);

        //spring-boot-loader 解决了appclassloader是普通的加载器不能加载嵌套的jar里面的文件
        // 运行时设置自定义加载器来实现嵌套jar资源加载
        new JarFileArchive(new File("/Users/mac/Downloads/app-cart-service.jar")).getNestedArchives(SpringClassloader::isNestedArchive);



    }


    protected static boolean  isNestedArchive(Archive.Entry entry) {
        return entry.isDirectory() ? entry.getName().equals("BOOT-INF/classes/") : entry.getName().startsWith("BOOT-INF/lib/");
    }
}
