package com.sifaki.webparser.geo.vk;

import java.io.IOException;
import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * @author SStorozhev
 * @since 3/23/2016
 */
public class CityVkParser {
    private static final String VK_BASE_URL = "https://api.vk.com/method/database.getCities";

    private HttpClient httpClient;

    public CityVkParser(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String parse(String toParse) throws IOException {
        final Iterable<String> tokens = Splitter.on(' ').split(toParse);
        final String firstToken = Iterables.getFirst(tokens, null);
        final String lastToken = Iterables.getLast(tokens, null);
        final String city = parseCity(firstToken);
        if (city != null) {
            return city;
        }
        return parseCity(lastToken);
    }

    private String parseCity(String firstToken) throws IOException {
        if (!Strings.isNullOrEmpty(firstToken)) {
            final GetMethod method = createGetMethod(VK_BASE_URL, firstToken);
            final int statusCode = httpClient.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                final String responseBody = method.getResponseBodyAsString();
                final VkResult vkResult = new Gson().fromJson(responseBody, VkResult.class);
                if (vkResult.getError() == null) {
                    final List<VkResult.Response> responses = vkResult.getResponse();
                    if (!responses.isEmpty()) {
                        return responses.iterator().next().getTitle();
                    }
                }
            }
        }
        return null;
    }

    private GetMethod createGetMethod(String url, String searchString) {
        final GetMethod method = new GetMethod(url);
        method.setQueryString(getQueryStringPairs(searchString));
        return method;
    }

    private NameValuePair[] getQueryStringPairs(String searchString) {
        NameValuePair[] nameValuePairs = new NameValuePair[3];
        nameValuePairs[0] = new NameValuePair("country_id", "2");
        nameValuePairs[1] = new NameValuePair("count", "1");
        nameValuePairs[2] = new NameValuePair("q", searchString);
        return nameValuePairs;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("httpClient", httpClient)
                .toString();
    }
}