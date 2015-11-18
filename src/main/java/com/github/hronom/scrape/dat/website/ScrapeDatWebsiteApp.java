package com.github.hronom.scrape.dat.website;

import com.github.hronom.scrape.dat.website.controllers.ScrapeButtonController;
import com.github.hronom.scrape.dat.website.views.ScrapeMainView;
import com.github.hronom.scrape.dat.website.views.ScrapeView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ScrapeDatWebsiteApp {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info(ScrapeDatWebsiteApp.class.getSimpleName());
        printSystemInfo();

        ScrapeView scrapeView = new ScrapeView();
        new ScrapeButtonController(scrapeView);
        new ScrapeMainView(scrapeView);
    }

    private static void printSystemInfo() {
        logger.info("Java version: " + System.getProperty("java.version"));
        logger.info("Java vendor: " + System.getProperty("java.vendor"));
        logger.info("Java vendor url: " + System.getProperty("java.vendor.url"));
        logger.info("Java home: " + System.getProperty("java.home"));
        logger.info("Java vm specification version: " + System.getProperty("java.vm.specification.version"));
        logger.info("Java vm specification vendor: " + System.getProperty("java.vm.specification.vendor"));
        logger.info("Java vm specification name: " + System.getProperty("java.vm.specification.name"));
        logger.info("Java vm version: " + System.getProperty("java.vm.version"));
        logger.info("Java vm vendor: " + System.getProperty("java.vm.vendor"));
        logger.info("Java vm name: " + System.getProperty("java.vm.name"));
        logger.info("Java specification version: " + System.getProperty("java.specification.version"));
        logger.info("Java specification vendor: " + System.getProperty("java.specification.vendor"));
        logger.info("Java specification name: " + System.getProperty("java.specification.name"));
        logger.info("Java class.version: " + System.getProperty("java.class.version"));
        logger.info("Java class.path: " + System.getProperty("java.class.path"));
        logger.info("Java library.path: " + System.getProperty("java.library.path"));
        logger.info("Java io.tmpdir: " + System.getProperty("java.io.tmpdir"));
        logger.info("Java compiler: " + System.getProperty("java.compiler"));
        logger.info("Java ext.dirs: " + System.getProperty("java.ext.dirs"));
        logger.info("OS name: " + System.getProperty("os.name"));
        logger.info("OS arch: " + System.getProperty("os.arch"));
        logger.info("OS version: " + System.getProperty("os.version"));


        // Total number of processors or cores available to the JVM.
        logger.info("Available processors (cores): " + Runtime.getRuntime().availableProcessors());
        // Total amount of free memory available to the JVM.
        logger.info("Free memory (bytes): " + Runtime.getRuntime().freeMemory());
        // This will return Long.MAX_VALUE if there is no preset limit.
        long maxMemory = Runtime.getRuntime().maxMemory();
        // Maximum amount of memory the JVM will attempt to use.
        logger.info("Maximum memory (bytes): " + (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));
        // Total memory currently in use by the JVM.
        logger.info("Total memory (bytes): " + Runtime.getRuntime().totalMemory());
        // Get a list of all filesystem roots on this system.
        File[] roots = File.listRoots();
        // For each filesystem root, print some info.
        for (File root : roots) {
            logger.info("File system root: " + root.getAbsolutePath());
            logger.info("Total space (bytes): " + root.getTotalSpace());
            logger.info("Free space (bytes): " + root.getFreeSpace());
            logger.info("Usable space (bytes): " + root.getUsableSpace());
        }
    }
}
