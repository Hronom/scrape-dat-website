package com.github.hronom.scrape.dat.website.views;

import java.awt.*;

import javax.swing.*;

public class ScrapeMainView {
    public ScrapeMainView(ScrapeView scrapeView) {
        JPanel mainPanel = new JPanel();
        mainPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        GridBagLayout layout = new GridBagLayout();
        mainPanel.setLayout(layout);

        GridBagConstraints constraint = new GridBagConstraints();
        constraint.insets = new Insets(3, 3, 3, 3);
        constraint.weightx = 1;
        constraint.weighty = 1;
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.gridwidth = 1;
        constraint.gridheight = 1;
        constraint.fill = GridBagConstraints.BOTH;
        mainPanel.add(scrapeView, constraint);

        JFrame frame = new JFrame("Scrape dat website!!!");
        frame.setContentPane(mainPanel);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
