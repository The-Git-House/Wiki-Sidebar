package com.example.wikisidebar;

import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Sidebar panel for the Wiki Sidebar plugin.
 * Contains a search bar and HTML display pane.
 */
public class WikiSidebarPanel extends PluginPanel
{
    private final JTextField searchField = new JTextField();
    private final JEditorPane wikiDisplay = new JEditorPane();

    public WikiSidebarPanel()
    {
        setLayout(new BorderLayout());

        // Search field at the top
        add(searchField, BorderLayout.NORTH);

        // HTML display area
        wikiDisplay.setContentType("text/html");
        wikiDisplay.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(wikiDisplay);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void displayPage(String html)
    {
        SwingUtilities.invokeLater(() -> {
            wikiDisplay.setText(html);
            wikiDisplay.setCaretPosition(0);
        });
    }

    public JTextField getSearchField()
    {
        return searchField;
    }
}
