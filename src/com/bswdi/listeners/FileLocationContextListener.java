package com.bswdi.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

@WebListener
public class FileLocationContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        String rootPath = System.getProperty("catalina.home");
        String rootPath = "/";
//        String rootPath = "/Users/liam/Desktop/Desktop - Liam’s MacBook Pro/Code/Java/AFC/WebContent/";
        ServletContext ctx = servletContextEvent.getServletContext();
        String relativePath = "FileStore";
        File file = new File(rootPath + File.separator + relativePath);
        boolean created = false;
        if (!file.exists()) created = file.mkdirs();
        if (created) System.out.println("File Directory created and to be used for storing files");
        else System.out.println("File Directory found and to be used for storing files");
        ctx.setAttribute("FILES_DIR_FILE", file);
        ctx.setAttribute("FILES_DIR", rootPath + File.separator + relativePath + File.separator);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
