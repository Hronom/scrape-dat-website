package com.github.hronom.scrape.dat.website.controllers;

import com.gargoylesoftware.htmlunit.AjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.github.hronom.scrape.dat.website.views.ScrapeView;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Executors;

public class ScrapeButtonController {
    private static final Logger logger = LogManager.getLogger();

    private final ScrapeView scrapeView;

    private final WebClient webClient;

    public ScrapeButtonController(ScrapeView scrapeViewArg) {
        scrapeView = scrapeViewArg;
        scrapeView.addScrapeButtonActionListener(createScrapeButtonActionListener());

        webClient = new WebClient(BrowserVersion.FIREFOX_38);
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setPopupBlockerEnabled(false);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.setAjaxController(new AjaxController() {
            @Override
            public boolean processSynchron(HtmlPage page, WebRequest request, boolean async) {
                return true;
            }
        });
    }

    public ActionListener createScrapeButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Executors.newSingleThreadExecutor().submit(new Runnable() {
                    public void run() {
                        logger.info("Start processing...");
                        long beginTime = System.currentTimeMillis();

                        // Output input parameters.
                        if (!scrapeView.getWebsiteUrl().isEmpty() &&
                            !scrapeView.getSelector().isEmpty()) {
                            logger.info("Input parameters: \"" +
                                        scrapeView.getWebsiteUrl() + "\", \"" +
                                        scrapeView.getSelector() + "\"");
                        }

                        // Disable fields in view.
                        scrapeView.setWebsiteUrlTextFieldEnabled(false);
                        scrapeView.setSelectorTextFieldEnabled(false);
                        scrapeView.setScrapeButtonEnabled(false);
                        scrapeView.setWorkInProgress(true);
                        scrapeView.setOutput("");

                        // Process.
                        try {
                            URL url = new URL(scrapeView.getWebsiteUrl());
                            logger.info("Requesting page...");
                            HtmlPage page = webClient.getPage(url);
                            logger.info("Requesting of page completed.");


                            logger.info("View page as XML");
                            String xml = page.asXml();

                            // Unescape html.
                            logger.info("Unescape html");
                            xml = StringEscapeUtils.unescapeHtml4(xml);

                            logger.info("Get selector");
                            String selector = scrapeView.getSelector();
                            if (!xml.isEmpty() && !selector.isEmpty()) {
                                logger.info("Parse XML");
                                Document doc = Jsoup.parse(xml);
                                Elements selectedElements = doc.select(selector);

                                if (!selectedElements.isEmpty()) {
                                    logger.info("Parse extracted elements");
                                    StringBuilder sb = new StringBuilder();
                                    for (Element element : selectedElements) {
                                        String body = element.html();
                                        sb.append(body);
                                        sb.append("\n");
                                        sb.append("\n");
                                    }
                                    scrapeView.setOutput(sb.toString());
                                }
                            }
                        } catch (IOException e) {
                            logger.error(e);
                        }

                        logger.info("Close WebClient.");
                        webClient.close();

                        // Enable fields in view.
                        scrapeView.setWorkInProgress(false);
                        scrapeView.setScrapeButtonEnabled(true);
                        scrapeView.setSelectorTextFieldEnabled(true);
                        scrapeView.setWebsiteUrlTextFieldEnabled(true);

                        long endTime = System.currentTimeMillis();
                        logger.info("Process time: " + (endTime - beginTime) + " ms.");
                        logger.info("Processing complete.");
                    }
                });
            }
        };
    }
}