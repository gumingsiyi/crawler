package com.my.crawler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
//import org.apache.http.config.Lookup;

import java.io.IOException;

/**
 * Created by Cao on 2015/7/4.
 */
public class HTTPUtils {
    public final static String getByURL(String url) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            HttpGet httpget = new HttpGet(url);

            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            return httpclient.execute(httpget, responseHandler);

        } finally {
            httpclient.close();
        }
    }
}
