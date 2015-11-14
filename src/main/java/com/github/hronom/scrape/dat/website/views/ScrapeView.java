package com.github.hronom.scrape.dat.website.views;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ScrapeView extends JPanel {
    private final JTextField websiteUrlTextField;
    private final JTextField selectorTextField;
    private final JButton scrapeButton;
    private final JTextArea outputTextArea;

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
            websiteUrlTextField = new JTextField("");

            constraint.weightx = 1;
            constraint.weighty = 0;
            constraint.gridx = 0;
            constraint.gridy = 0;
            constraint.gridwidth = 1;
            constraint.gridheight = 1;
            constraint.fill = GridBagConstraints.BOTH;
            this.add(websiteUrlTextField, constraint);
        }

        {
            selectorTextField = new JTextField("");

            constraint.weightx = 1;
            constraint.weighty = 0;
            constraint.gridx = 0;
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
            constraint.gridwidth = 1;
            constraint.gridheight = 1;
            constraint.fill = GridBagConstraints.BOTH;
            this.add(scrapeButton, constraint);
        }

        {
            outputTextArea = new JTextArea();
            outputTextArea.setWrapStyleWord(true);
            outputTextArea.setAutoscrolls(true);

            JScrollPane scrollPane = new JScrollPane(outputTextArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

            constraint.weightx = 1;
            constraint.weighty = 1;
            constraint.gridx = 0;
            constraint.gridy = 3;
            constraint.gridwidth = 1;
            constraint.gridheight = 1;
            constraint.fill = GridBagConstraints.BOTH;
            this.add(scrollPane, constraint);
        }
    }

    public void addScrapeButtonActionListener(ActionListener actionListener) {
        scrapeButton.addActionListener(actionListener);
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
}
