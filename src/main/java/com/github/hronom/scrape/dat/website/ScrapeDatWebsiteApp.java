package com.github.hronom.scrape.dat.website;

import com.github.hronom.scrape.dat.website.controllers.ScrapeButtonController;
import com.github.hronom.scrape.dat.website.views.ScrapeMainView;
import com.github.hronom.scrape.dat.website.views.ScrapeView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScrapeDatWebsiteApp {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info(ScrapeDatWebsiteApp.class.getSimpleName());

        ScrapeView scrapeView = new ScrapeView();
        new ScrapeButtonController(scrapeView);
        new ScrapeMainView(scrapeView);
    }
}
