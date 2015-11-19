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

import com.ui4j.api.browser.BrowserEngine;
import com.ui4j.api.browser.BrowserFactory;
import com.ui4j.api.browser.Page;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.concurrent.Executors;

public class ScrapeButtonController {
    private static final Logger logger = LogManager.getLogger();

    private final ScrapeView scrapeView;

    public ScrapeButtonController(ScrapeView scrapeViewArg) {
        scrapeView = scrapeViewArg;
        scrapeView.addScrapeButtonActionListener(createScrapeButtonActionListener2());
    }

    public ActionListener createScrapeButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Executors.newSingleThreadExecutor().submit(new Runnable() {
                    public void run() {
                        // Disable fields in view.
                        scrapeView.setWebsiteUrlTextFieldEnabled(false);
                        scrapeView.setSelectorTextFieldEnabled(false);
                        scrapeView.setScrapeButtonEnabled(false);
                        scrapeView.setWorkInProgress(true);
                        scrapeView.setOutput("");

                        scrapeView.setProgressBarTaskText("initializing");
                        logger.info("Start processing...");
                        long beginTime = System.currentTimeMillis();

                        // Output input parameters.
                        if (!scrapeView.getWebsiteUrl().isEmpty() &&
                            !scrapeView.getSelector().isEmpty()) {
                            logger.info("Input parameters: \"" +
                                        scrapeView.getWebsiteUrl() + "\", \"" +
                                        scrapeView.getSelector() + "\", \"");
                        }

                        // Process.
                        try (WebClient webClient = createWebClient()) {
                            URL url = new URL(scrapeView.getWebsiteUrl());
                            scrapeView.setProgressBarTaskText("requesting page");
                            logger.info("Requesting page...");
                            HtmlPage page = webClient.getPage(url);
                            logger.info("Requesting of page completed.");

                            scrapeView.setProgressBarTaskText("viewing page as XML");
                            logger.info("View page as XML");
                            String xml = page.asXml();

                            // Unescape html.
                            scrapeView.setProgressBarTaskText("unescaping HTML");
                            logger.info("Unescape html");
                            xml = StringEscapeUtils.unescapeHtml4(xml);

                            logger.info("Get selector");
                            String selector = scrapeView.getSelector();
                            if (!xml.isEmpty() && !selector.isEmpty()) {
                                scrapeView.setProgressBarTaskText("parsing HTML");
                                logger.info("Parse HTML");
                                Document doc = Jsoup.parse(xml);

                                scrapeView.setProgressBarTaskText("selecting elements in HTML");
                                logger.info("select elements in HTML");
                                Elements selectedElements = doc.select(selector);

                                if (!selectedElements.isEmpty()) {
                                    scrapeView.setProgressBarTaskText("parsing selected elements");
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
                        } catch (Exception e) {
                            logger.error(e);
                        }

                        long endTime = System.currentTimeMillis();
                        logger.info("Process time: " + (endTime - beginTime) + " ms.");
                        logger.info("Processing complete.");

                        // Enable fields in view.
                        scrapeView.setWorkInProgress(false);
                        scrapeView.setScrapeButtonEnabled(true);
                        scrapeView.setSelectorTextFieldEnabled(true);
                        scrapeView.setWebsiteUrlTextFieldEnabled(true);
                    }
                });
            }
        };
    }

    public ActionListener createScrapeButtonActionListener2() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Executors.newSingleThreadExecutor().submit(new Runnable() {
                    public void run() {
                        // Disable fields in view.
                        scrapeView.setWebsiteUrlTextFieldEnabled(false);
                        scrapeView.setSelectorTextFieldEnabled(false);
                        scrapeView.setScrapeButtonEnabled(false);
                        scrapeView.setWorkInProgress(true);
                        scrapeView.setOutput("");

                        scrapeView.setProgressBarTaskText("initializing");
                        logger.info("Start processing...");
                        long beginTime = System.currentTimeMillis();

                        // Output input parameters.
                        if (!scrapeView.getWebsiteUrl().isEmpty() &&
                            !scrapeView.getSelector().isEmpty()) {
                            logger.info("Input parameters: \"" +
                                        scrapeView.getWebsiteUrl() + "\", \"" +
                                        scrapeView.getSelector() + "\", \"");
                        }

                        // Process.
                        BrowserEngine browserEngine = createBrowserEngine();

                        // Navigate to blank page.
                        scrapeView.setProgressBarTaskText("requesting page");
                        logger.info("Requesting page...");
                        Page page = browserEngine.navigate(scrapeView.getWebsiteUrl());
                        logger.info("Requesting of page completed.");

                        scrapeView.setProgressBarTaskText("viewing page as XML");
                        logger.info("View page as XML");
                        String html = page.getDocument().getBody().getInnerHTML();;

                        // Unescape html.
                        scrapeView.setProgressBarTaskText("unescaping HTML");
                        logger.info("Unescape html");
                        html = StringEscapeUtils.unescapeHtml4(html);

                        logger.info("Get selector");
                        String selector = scrapeView.getSelector();
                        if (!html.isEmpty() && !selector.isEmpty()) {
                            scrapeView.setProgressBarTaskText("parsing HTML");
                            logger.info("Parse HTML");
                            Document doc = Jsoup.parse(html);

                            scrapeView.setProgressBarTaskText("selecting elements in HTML");
                            logger.info("select elements in HTML");
                            Elements selectedElements = doc.select(selector);

                            if (!selectedElements.isEmpty()) {
                                scrapeView.setProgressBarTaskText("parsing selected elements");
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

                        long endTime = System.currentTimeMillis();
                        logger.info("Process time: " + (endTime - beginTime) + " ms.");
                        logger.info("Processing complete.");

                        // Enable fields in view.
                        scrapeView.setWorkInProgress(false);
                        scrapeView.setScrapeButtonEnabled(true);
                        scrapeView.setSelectorTextFieldEnabled(true);
                        scrapeView.setWebsiteUrlTextFieldEnabled(true);
                    }
                });
            }
        };
    }

    private WebClient createWebClient() {
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setPopupBlockerEnabled(false);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setActiveXNative(true);
        webClient.getOptions().setAppletEnabled(true);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.setAjaxController(new AjaxController() {
            @Override
            public boolean processSynchron(HtmlPage page, WebRequest request, boolean async) {
                return true;
            }
        });
        return webClient;
    }

    private BrowserEngine createBrowserEngine() {
        // Get the instance of the webkit.
        BrowserEngine browser = BrowserFactory.getWebKit();

        // Navigate to blank page.
        // Page page = browser.navigate("about:blank");

        // Show the browser page.
        //page.show();
        //System.setProperty("ui4j.headless", "true");

        //page.getDocument().getBody().getInnerHTML();

        // Append html header to the document body.
        //page.getDocument().getBody().append("<h1>Hello, World!</h1>");
        return browser;
    }
}