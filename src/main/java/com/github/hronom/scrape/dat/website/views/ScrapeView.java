package com.github.hronom.scrape.dat.website.views;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ScrapeView extends JPanel {
    private final JLabel websiteUrlLabel;
    private final JTextField websiteUrlTextField;
    private final JLabel selectorLabel;
    private final JTextField selectorTextField;
    private final JButton scrapeButton;
    private final JTextArea outputTextArea;
    private final JProgressBar progressBar;

    public ScrapeView() {
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);

        GridBagConstraints constraint = new GridBagConstraints();
        constraint.insets = new Insets(3, 3, 3, 3);
        constraint.weightx = 1;
        constraint.weighty = 0;
        constraint.gridwidth = 1;
        constraint.anchor = GridBagConstraints.CENTER;

        {
            websiteUrlLabel = new JLabel("Website URL:");

            constraint.weightx = 0;
            constraint.weighty = 0;
            constraint.gridx = 0;
            constraint.gridy = 0;
            constraint.gridwidth = 1;
            constraint.gridheight = 1;
            constraint.fill = GridBagConstraints.BOTH;
            this.add(websiteUrlLabel, constraint);
        }

        {
            websiteUrlTextField = new JTextField("");

            constraint.weightx = 1;
            constraint.weighty = 0;
            constraint.gridx = 1;
            constraint.gridy = 0;
            constraint.gridwidth = 1;
            constraint.gridheight = 1;
            constraint.fill = GridBagConstraints.BOTH;
            this.add(websiteUrlTextField, constraint);
        }

        {
            selectorLabel = new JLabel("Selector:");

            constraint.weightx = 0;
            constraint.weighty = 0;
            constraint.gridx = 0;
            constraint.gridy = 1;
            constraint.gridwidth = 1;
            constraint.gridheight = 1;
            constraint.fill = GridBagConstraints.BOTH;
            this.add(selectorLabel, constraint);
        }

        {
            selectorTextField = new JTextField("");

            constraint.weightx = 1;
            constraint.weighty = 0;
            constraint.gridx = 1;
            constraint.gridy = 1;
            constraint.gridwidth = 1;
            constraint.gridheight = 1;
            constraint.fill = GridBagConstraints.BOTH;
            this.add(selectorTextField, constraint);
        }

        {
            scrapeButton = new JButton("Scrape website");

            constraint.weightx = 1;
            constraint.weighty = 0;
            constraint.gridx = 0;
            constraint.gridy = 2;
            constraint.gridwidth = 2;
            constraint.gridheight = 1;
            constraint.fill = GridBagConstraints.BOTH;
            this.add(scrapeButton, constraint);
        }

        {
            outputTextArea = new JTextArea();
            outputTextArea.setEditable(false);
            outputTextArea.setWrapStyleWord(false);
            outputTextArea.setAutoscrolls(true);

            JScrollPane scrollPane = new JScrollPane(outputTextArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

            constraint.weightx = 1;
            constraint.weighty = 1;
            constraint.gridx = 0;
            constraint.gridy = 3;
            constraint.gridwidth = 2;
            constraint.gridheight = 1;
            constraint.fill = GridBagConstraints.BOTH;
            this.add(scrollPane, constraint);
        }

        {
            progressBar = new JProgressBar();
            progressBar.setString("Working...");
            progressBar.setStringPainted(true);
            progressBar.setIndeterminate(true);
            progressBar.setVisible(false);

            constraint.weightx = 1;
            constraint.weighty = 0;
            constraint.gridx = 0;
            constraint.gridy = 4;
            constraint.gridwidth = 2;
            constraint.gridheight = 1;
            constraint.fill = GridBagConstraints.BOTH;
            this.add(progressBar, constraint);
        }
    }

    public void addScrapeButtonActionListener(ActionListener actionListener) {
        scrapeButton.addActionListener(actionListener);
    }

    public void setWebsiteUrlTextFieldEnabled(boolean enabled) {
        websiteUrlTextField.setEnabled(enabled);
    }

    public void setSelectorTextFieldEnabled(boolean enabled) {
        selectorTextField.setEnabled(enabled);
    }

    public void setScrapeButtonEnabled(boolean enabled) {
        scrapeButton.setEnabled(enabled);
    }

    public String getWebsiteUrl() {
        return websiteUrlTextField.getText();
    }

    public String getSelector() {
        return selectorTextField.getText();
    }

    public void setOutput(String text) {
        outputTextArea.setText(text);
    }

    public void setWorkInProgress(boolean working){
        progressBar.setVisible(working);
    }
}
