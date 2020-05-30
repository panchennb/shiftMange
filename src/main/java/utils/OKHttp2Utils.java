package utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OKHttp2Utils {
    //代码提交媒体格式
    public static final MediaType MEDIA_TYPE_JSON = MediaType
            .parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType
            .parse("text/x-markdown; charset=utf-8");
    public static final MediaType MEDIA_TYPE_FORM = MediaType
            .parse("application/x-www-form-urlencoded");
    public static final MediaType MEDIA_TYPE_XML = MediaType.parse("text/xml;charset=utf-8");
    private static final Logger LOGGER = LoggerFactory.getLogger(OKHttp2Utils.class);
    //    private static final Logger LOGGER = Logger.getLogger(OKHttp2Utils.class);
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static OkHttpClient okHttpClient = null;

    static {
        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(180, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(180, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(180, TimeUnit.SECONDS);
        okHttpClient.setRetryOnConnectionFailure(true);
    }

    /**
     * 同步GET请求
     */
    public static String get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response;
        String result = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                LOGGER.error(String.format("[Unexpected code %s]", response));
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        } catch (IOException e) {
            LOGGER.error(String.format("[%s]", e.getMessage()));
        }
        return result;
    }

    public static String doGet(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(1, TimeUnit.MINUTES);
        client.setReadTimeout(1, TimeUnit.MINUTES);
        client.setWriteTimeout(1, TimeUnit.MINUTES);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 同步GET请求
     */
    public static byte[] getBytes(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response;
        byte[] result = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                LOGGER.error(String.format("[Unexpected code %s]", response));
                throw new IOException("Unexpected code " + response);
            }
            return response.body().bytes();
        } catch (IOException e) {
            LOGGER.error(String.format("[%s]", e.getMessage()));
        }
        return result;
    }

    /**
     * 同步GET请求
     */
    public static String get(String url, Map<String, String> header) throws IOException {
        Request.Builder builder = new Request.Builder();
        if (header != null && header.size() > 0) {
            for (Iterator ite = header.entrySet().iterator(); ite.hasNext(); ) {
                Map.Entry entry = (Map.Entry) ite.next();
                if (entry != null) {
                    builder.addHeader(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
            }

        }
        Request request = builder.url(url).build();

        Response response;
        String result = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        } catch (IOException e) {
            LOGGER.error(String.format("[%s]", e.getMessage()));
        }
        return result;
    }

    /**
     * 同步GET请求
     */
    public static String getIgnoreRes(String url, Map<String, String> header) throws IOException {
        Request.Builder builder = new Request.Builder();
        if (header != null && header.size() > 0) {
            for (Iterator ite = header.entrySet().iterator(); ite.hasNext(); ) {
                Map.Entry entry = (Map.Entry) ite.next();
                if (entry != null) {
                    builder.addHeader(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
            }

        }
        Request request = builder.url(url).build();

        Response response;
        String result = null;
        try {
            response = okHttpClient.newCall(request).execute();

            return response.body().string();
        } catch (IOException e) {
            LOGGER.error(String.format("[%s]", e.getMessage()));
        }
        return result;
    }

    /**
     * 发送GET请求并完成URL拼接
     */
    public static String sendGet(String url, List<String[]> parameters) {
        Request.Builder builder = new Request.Builder();
        StringBuffer sb = new StringBuffer();// 存储参数
        String params = "";// 编码之后的参数
        for (String[] item : parameters) {
            try {
                sb.append(item[0]).append("=")
                        .append(java.net.URLEncoder.encode(item[1], "UTF-8"))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("编码失败", e);
            }
        }

        params = sb.toString();
        if (!StringUtils.isEmpty(params)) {
            params = params.substring(0, params.length() - 1);
            url += "?" + params;
        }
        //发送Get请求
        Request request = builder.url(url).build();
        Response response;
        String result = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        } catch (IOException e) {
            LOGGER.error(String.format("[%s]", e.getMessage()));
        }
        return result;
    }

    /**
     * 同步GET请求
     */
    public static byte[] getBytes(String url, Map<String, String> header) throws IOException {
        Request.Builder builder = new Request.Builder();
        if (header != null && header.size() > 0) {
            for (Iterator ite = header.entrySet().iterator(); ite.hasNext(); ) {
                Map.Entry entry = (Map.Entry) ite.next();
                if (entry != null) {
                    builder.addHeader(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
            }
        }
        Request request = builder.url(url).build();

        Response response;
        byte[] result = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().bytes();

        } catch (IOException e) {
            LOGGER.error(String.format("[%s]", e.getMessage()));
        }

        return result;
    }

    /**
     * 异步GET请求
     */
    public static String asycGet(String url) {
        final Request request = new Request.Builder()
                .url(url)
                .build();

        final String[] result = {""};
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                LOGGER.error(String.format("[%s]", e.getMessage()));
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    LOGGER.debug(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                result[0] = response.body().string();
            }

        });

        return result[0];
    }

    /**
     * 异步GET请求
     */
    public static String asycGet(String url, Map<String, String> header) {
        Request.Builder builder = new Request.Builder();
        if (header != null && header.size() > 0) {
            for (Iterator ite = header.entrySet().iterator(); ite.hasNext(); ) {
                Map.Entry entry = (Map.Entry) ite.next();
                if (entry != null) {
                    builder.addHeader(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
            }
        }
        Request request = builder.url(url).build();

        final String[] result = {""};

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                LOGGER.error(String.format("[%s]", e.getMessage()));
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                result[0] = response.body().string();
            }

        });

        return result[0];
    }

    /**
     * post提交JSON
     */
    public static String postJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static String sendPost(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            String code = String.valueOf(response.code());
            return StringUtils.isEmpty(code) ? "200" : code;
        }
        return response.body().string();
    }

    public static String syncJavaSendPost(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    /**
     * post提交JSON
     */
    public static String postJson(String url, String json, Map<String, String> header)
            throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request.Builder builder = new Request.Builder();

        if (header != null && header.size() > 0) {

//        使用泛型
//        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
//        Iterator<Map.Entry<Integer, Integer>> entries = map.entrySet().iterator();
//        while (entries.hasNext()) {
//            Map.Entry<Integer, Integer> entry = entries.next();
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

            for (Iterator ite = header.entrySet().iterator(); ite.hasNext(); ) {
                Map.Entry entry = (Map.Entry) ite.next();
                if (entry != null) {
                    builder.addHeader(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
            }

        }
        Request request = builder
                .url(url)
                .post(body)
                .build();

        Response response = null;
        response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static String postJsonIgnoreRes(String url, String json, Map<String, String> header)
            throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request.Builder builder = new Request.Builder();

        if (header != null && header.size() > 0) {

            for (Iterator ite = header.entrySet().iterator(); ite.hasNext(); ) {
                Map.Entry entry = (Map.Entry) ite.next();
                if (entry != null) {
                    builder.addHeader(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
            }

        }
        Request request = builder
                .url(url)
                .post(body)
                .build();

        Response response = null;
        response = okHttpClient.newCall(request).execute();
        return response.body().string();

    }

    /**
     * put提交JSON
     */
    public static String putJson(String url, String json, Map<String, String> header)
            throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request.Builder builder = new Request.Builder();

        if (header != null && header.size() > 0) {
            for (Iterator ite = header.entrySet().iterator(); ite.hasNext(); ) {
                Map.Entry entry = (Map.Entry) ite.next();
                if (entry != null) {
                    builder.addHeader(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
            }
        }
        Request request = builder
                .url(url)
                .put(body)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static String postJson(String url, String json, String contentType)
            throws IOException {
        String type = contentType + "; charset=utf-8";
        MediaType mediaType = MediaType.parse(type);
        RequestBody body = RequestBody.create(mediaType, json);
        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url(url)
                .post(body)
                .build();

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (InterruptedIOException e) {
            LOGGER.error("Thread has been interrupted;");
        }
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * post提交String
     */
    public static String postString(String url, String requestBody) throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_FORM, requestBody);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static String postWwwUrlString(String url, Map<String, String> paramsMap) throws IOException {

        StringBuilder urlParam = new StringBuilder();
        for (Map.Entry<String, String> map : paramsMap.entrySet()) {
            urlParam.append(map.getKey()).append("=").append(map.getValue()).append("&");
        }
        urlParam.append("undefined=");

        RequestBody body = RequestBody.create(MEDIA_TYPE_FORM, urlParam.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String postAppLoginString(String url, String json, Map<String, String> header) throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request.Builder builder = new Request.Builder();
        if (header != null && header.size() > 0) {
            for (Iterator ite = header.entrySet().iterator(); ite.hasNext(); ) {
                Map.Entry entry = (Map.Entry) ite.next();
                if (entry != null) {
                    builder.addHeader(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
            }

        }
        Request request = builder
                .url(url)
                .post(body)
                .addHeader("cache-control", "no-cache")
                .build();
        LOGGER.info(String.valueOf(JSONObject.fromObject(builder)));
        Response response = okHttpClient.newCall(request).execute();

        return response.body().string();
    }

    /**
     * post提交XML
     */
    public static String postXml(String url, String xml) throws IOException {
        RequestBody body = RequestBody.create(MEDIA_TYPE_XML, xml);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static String postApiObj(String url, Object jsonObj) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        String json = objectMapper.writeValueAsString(jsonObj);

        return OKHttp2Utils.postJson(url, json, "application/json");
    }

    public static String putApiObj(String url, Object jsonObj, Map<String, String> header) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        String json = objectMapper.writeValueAsString(jsonObj);

        return OKHttp2Utils.putJson(url, json, header);
    }

    public static String postApiObj(String url, Object jsonObj, Map<String, String> header) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        String json = objectMapper.writeValueAsString(jsonObj);

        return OKHttp2Utils.postJson(url, json, header);
    }

    public static String postApiObjIgnoreRes(String url, Object jsonObj, Map<String, String> header) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        String json = objectMapper.writeValueAsString(jsonObj);

        return OKHttp2Utils.postJsonIgnoreRes(url, json, header);
    }

    /**
     * post提交，解决SSL证书问题报错
     */
    public static String postJson4SSL(String url, String json, Map<String, String> header) throws Exception {
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        SSLSocketFactory sslSocketFactory = null;
        SSLContext sslContext;
        sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new X509TrustManager[]{trustManager}, null);
        sslSocketFactory = sslContext.getSocketFactory();
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request.Builder builder = new Request.Builder();
        okHttpClient.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                //强行返回true 即验证成功
                return true;
            }
        }).setSslSocketFactory(sslSocketFactory);
        if (header != null && header.size() > 0) {
            for (Iterator ite = header.entrySet().iterator(); ite.hasNext(); ) {
                Map.Entry entry = (Map.Entry) ite.next();
                if (entry != null) {
                    builder.addHeader(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
            }

        }
        Request request = builder
                .url(url)
                .post(body)
                .build();

        Response response = null;
        response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new Exception("Unexpected code " + response);
        }
    }

    /**
     * get提交，解决SSL证书问题报错
     */
    public static String get4SSL(String url, Map<String, String> header) throws Exception {
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        SSLSocketFactory sslSocketFactory = null;
        SSLContext sslContext;
        sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new X509TrustManager[]{trustManager}, null);
        sslSocketFactory = sslContext.getSocketFactory();
        okHttpClient.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                //强行返回true 即验证成功
                return true;
            }
        }).setSslSocketFactory(sslSocketFactory);
        Request.Builder builder = new Request.Builder();
        if (header != null && header.size() > 0) {
            for (Iterator ite = header.entrySet().iterator(); ite.hasNext(); ) {
                Map.Entry entry = (Map.Entry) ite.next();
                if (entry != null) {
                    builder.addHeader(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
                }
            }

        }
        Request request = builder
                .url(url)
                .get()
                .addHeader("cache-control", "no-cache")
                //.addHeader("postman-token", "98b5753b-0417-02c9-44f3-fa7b845f78f4")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new Exception("Unexpected code " + response);
        }
    }

    public static String post(String url) throws Exception {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{}");
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public static String doPost(String geturl, String s) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, s);
        Request request = new Request.Builder()
                .url(geturl)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Source", "no-501")
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
