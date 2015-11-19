package com.github.hronom.scrape.dat.website.controllers;

import com.gargoylesoftware.htmlunit.AjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.github.hronom.scrape.dat.website.views.ScrapeView;
import com.ui4j.api.browser.BrowserEngine;
import com.ui4j.api.browser.BrowserFactory;
import com.ui4j.api.browser.Page;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.concurrent.Executors;

public class ScrapeButtonController {
    private static final Logger logger = LogManager.getLogger();

    private final ScrapeView scrapeView;

    private final WebClient webClient;
    private final BrowserEngine browserEngine;

    public ScrapeButtonController(ScrapeView scrapeViewArg) {
        scrapeView = scrapeViewArg;
        scrapeView.addScrapeButtonActionListener(createScrapeButtonActionListener());

        // Create HTMLUnit WebClient.
        {
            webClient = new WebClient(BrowserVersion.FIREFOX_38);
            webClient.getOptions().setCssEnabled(true);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setPopupBlockerEnabled(false);
            webClient.getOptions().setRedirectEnabled(true);
            webClient.getOptions().setActiveXNative(true);
            webClient.getOptions().setAppletEnabled(true);
            webClient.getOptions().setUseInsecureSSL(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getCookieManager().setCookiesEnabled(true);
            webClient.setAjaxController(new AjaxController() {
                @Override
                public boolean processSynchron(HtmlPage page, WebRequest request, boolean async) {
                    return true;
                }
            });
        }

        // Create Ui4j BrowserEngine.
        {
            browserEngine = BrowserFactory.getWebKit();
        }
    }

    public ActionListener createScrapeButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Executors.newSingleThreadExecutor().submit(new Runnable() {
                    public void run() {
                        if (scrapeView.isUi4jEnabled()) {
                            processByUi4j();
                        } else {
                            processByHtmlUnit();
                        }
                    }
                });
            }
        };
    }

    public void processByHtmlUnit() {
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
        if (!scrapeView.getWebsiteUrl().isEmpty() && !scrapeView.getSelector().isEmpty()) {
            logger.info("Input parameters: \"" +
                        scrapeView.getWebsiteUrl() + "\", \"" +
                        scrapeView.getSelector() + "\", \"");
        }

        // Process.
        try {
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

        webClient.close();

        long endTime = System.currentTimeMillis();
        logger.info("Process time: " + (endTime - beginTime) + " ms.");
        logger.info("Processing complete.");

        // Enable fields in view.
        scrapeView.setWorkInProgress(false);
        scrapeView.setScrapeButtonEnabled(true);
        scrapeView.setSelectorTextFieldEnabled(true);
        scrapeView.setWebsiteUrlTextFieldEnabled(true);
    }

    public void processByUi4j() {
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
        if (!scrapeView.getWebsiteUrl().isEmpty() && !scrapeView.getSelector().isEmpty()) {
            logger.info("Input parameters: \"" +
                        scrapeView.getWebsiteUrl() + "\", \"" +
                        scrapeView.getSelector() + "\", \"");
        }

        // Navigate to blank page.
        scrapeView.setProgressBarTaskText("requesting page");
        logger.info("Requesting page...");
        Page page = browserEngine.navigate(scrapeView.getWebsiteUrl());
        //page.show();
        logger.info("Requesting of page completed.");

        scrapeView.setProgressBarTaskText("viewing page as HTML");
        logger.info("View page as HTML");
        String html = page.getDocument().getBody().getInnerHTML();

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

        browserEngine.clearCookies();

        long endTime = System.currentTimeMillis();
        logger.info("Process time: " + (endTime - beginTime) + " ms.");
        logger.info("Processing complete.");

        // Enable fields in view.
        scrapeView.setWorkInProgress(false);
        scrapeView.setScrapeButtonEnabled(true);
        scrapeView.setSelectorTextFieldEnabled(true);
        scrapeView.setWebsiteUrlTextFieldEnabled(true);
    }
}