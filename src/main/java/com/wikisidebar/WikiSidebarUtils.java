package com.example.wikisidebar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Utility for fetching wiki page HTML safely using RuneLite's OkHttp dependency.
 */
public class WikiSidebarUtils
{
    private static final OkHttpClient CLIENT = new OkHttpClient();

    public static String fetchHtml(String url) throws Exception
    {
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "RuneLite-WikiSidebarPlugin")
                .build();

        try (Response response = CLIENT.newCall(request).execute())
        {
            if (!response.isSuccessful())
            {
                throw new Exception("HTTP " + response.code() + " — " + response.message());
            }
            return response.body().string();
        }
    }
}
