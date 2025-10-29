package com.example.wikisidebar;

import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.plugins.wiki.WikiPlugin;

import javax.inject.Inject;

/**
 * Wiki Sidebar Plugin
 * Displays OSRS Wiki pages inside RuneLite’s sidebar using the built-in Wiki plugin.
 */
@PluginDescriptor(
    name = "Wiki Sidebar",
    description = "Displays OSRS Wiki pages directly inside RuneLite using the built-in Wiki plugin",
    tags = {"wiki", "sidebar", "osrs"}
)
public class WikiSidebarPlugin extends Plugin
{
    @Inject
    private ClientToolbar clientToolbar;

    @Inject
    private WikiPlugin wikiPlugin;

    private WikiSidebarPanel panel;
    private NavigationButton navButton;

    @Override
    protected void startUp()
    {
        panel = new WikiSidebarPanel();

        // Handle search submissions
        panel.getSearchField().addActionListener(e ->
        {
            String query = panel.getSearchField().getText().trim();
            if (!query.isEmpty())
            {
                loadWikiPage(query);
            }
        });

        navButton = NavigationButton.builder()
                .tooltip("Wiki Sidebar")
                .icon(null)
                .priority(5)
                .panel(panel)
                .build();

        clientToolbar.addNavigation(navButton);
    }

    private void loadWikiPage(String pageName)
    {
        new Thread(() ->
        {
            try
            {
                // Instead of wikiManager, use RuneLite’s wiki base directly:
                String wikiBaseUrl = "https://oldschool.runescape.wiki";
                String pageUrl = wikiBaseUrl + "/w/" + pageName.replace(" ", "_");

                String html = WikiSidebarUtils.fetchHtml(pageUrl);
                panel.displayPage(html);
            }
            catch (Exception ex)
            {
                panel.displayPage("<html><body><h3>⚠️ Error loading wiki page.</h3><p>" +
                        ex.getMessage() + "</p></body></html>");
            }
        }).start();
    }

    @Override
    protected void shutDown()
    {
        clientToolbar.removeNavigation(navButton);
    }
}
