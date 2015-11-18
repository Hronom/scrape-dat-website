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

        ScrapeView scrapeView = new ScrapeView();
        new ScrapeButtonController(scrapeView);
        new ScrapeMainView(scrapeView);
    }
}
