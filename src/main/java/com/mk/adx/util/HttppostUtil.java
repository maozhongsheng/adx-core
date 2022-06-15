package com.mk.adx.util;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.util.JsonFormat;
import com.mk.adx.entity.json.request.PostUtilDTO;
import com.mk.adx.entity.json.request.tz.TzBidRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * http请求工具类
 *
 * @author yjn
 * @version 1.0
 * @date 2021/3/11 16:51
 */
@Slf4j
public class HttppostUtil {

    /**
     * json格式提交参数
     * @param params 参数
     * @return
     * @throws IOException
     */
    public static String doJsonPost(PostUtilDTO params){

        // 创建一个post请求
        HttpPost post = new HttpPost(params.getUrl());
        post.setHeader("accept", "*/*");
        post.setHeader("connection", "Keep-Alive");
        post.setHeader("Content-Type", "application/json;charset=UTF-8");

        //设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(500).setConnectionRequestTimeout(200)
                .setSocketTimeout(500).build();
        post.setConfig(requestConfig);

        //ua
        if(StringUtils.isNotBlank(params.getUa())){
            post.setHeader("User-Agent",params.getUa());
        }
        //同城header
        if(StringUtils.isNotBlank(params.getHeaderTongcheng())){
            post.setHeader("adp-api-caller","adp.api.tz");
        }
        //云聚合header
        if (StringUtils.isNotBlank(params.getHeaderYunJuHe())){
            post.setHeader("Accept-Encoding", "gzip");
        }
        //沃氪header
        if (StringUtils.isNotBlank(params.getHeaderWoKe())){
            post.setHeader("Accept-Encoding", "gzip");
        }
        //数字悦动header
        if (StringUtils.isNotBlank(params.getHeaderSzyd())){
            post.setHeader("Accept-Encoding", "gzip");
        }
        //数字悦动header
        if (StringUtils.isNotBlank(params.getSzyd_ip())){
            post.setHeader("X-Forwarded-For", params.getSzyd_ip());
        }
        //请求参数体
        if(StringUtils.isNotBlank(params.getContent())){
            post.setEntity(new StringEntity(params.getContent(), "utf-8"));
        }

        //创建客户端
        HttpClient httpClient = HttpClients.createDefault();

        //发送请求
        String result=null;
        try {
            HttpResponse response = httpClient.execute(post);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200){
                log.info("请求成功"+code);
                result=getCodeAndResult(response);
            }else if (code == 204){
                log.info("204无数据！");
            } else {
                log.info("请求失败"+code);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 根据响应结果获取状态码和body
     *
     * @param response 响应结果
     * @throws IOException
     */
    private static String getCodeAndResult(HttpResponse response) throws IOException {
        // 获取状态码
        //int code = response.getStatusLine().getStatusCode();
        //System.out.println(code);
        // 获取body
        return EntityUtils.toString(response.getEntity(),"UTF-8");
    }



    /**
     * protobuf格式提交参数
     * @param post 接口地址
     * @param message 参数
     * @param
     * @return
     * @throws IOException
     */
    public static HttpResponse doProtobufPost(HttpPost post, GeneratedMessageV3 message, String ua) throws IOException {
        HttpClient httpclient = HttpClients.createDefault();
        String requestUrl = post.getURI().toString();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(message.toByteArray());
        InputStreamEntity inputStreamEntity = new InputStreamEntity(inputStream);
        post.setEntity(inputStreamEntity);

        post.addHeader("Content-Type", "application/x-protobuf");
        post.addHeader("User-Agent",ua);
        //设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(500).setConnectionRequestTimeout(200)
                .setSocketTimeout(500).build();
        post.setConfig(requestConfig);

        for (Header header : post.getAllHeaders()) {
            // System.out.println(header.getName() + ":" + header.getValue());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("curl -XPOST ");
        for (Header header : post.getAllHeaders()) {
            sb.append(" -H \"").append(header.getName()).append(":").append(header.getValue()).append("\"");
        }

        String jsonBody = JsonFormat.printer().print(message);
        jsonBody = jsonBody.replace("\"", "\\\"");
        sb.append(" -d \"").append(jsonBody).append("\"");
        sb.append(" ").append(requestUrl);

        //    System.out.println(sb.toString());
        return httpclient.execute(post);
    }
    /**
     * json格式提交参数
     * @param uri 接口地址
     * @param params 参数
     * @param ua
     * @return
     * @throws IOException
     */
    public static String doUcJsonPost(String uri, byte[] params,String ua){

        // 创建一个post请求
        HttpPost post = new HttpPost(uri);
        post.setHeader("Content-Type", "application/json");
        //设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(500).setConnectionRequestTimeout(200)
                .setSocketTimeout(500).build();
        post.setConfig(requestConfig);

        String result=null;
        //设置参数
        ByteArrayEntity arrayEntity = new ByteArrayEntity(params);
        post.setEntity(arrayEntity);

        //ua
        if(!StringUtils.isBlank(ua)){
            post.setHeader("User-Agent",ua);
        }
        //创建客户端
        HttpClient httpClient = HttpClients.createDefault();

        HttpEntity entity = null;
        byte[] responseContent = null;
        //发送请求
        try {
            HttpResponse response = httpClient.execute(post);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200){
                log.info("请求成功"+code);
                entity = response.getEntity();
                responseContent = EntityUtils.toByteArray(entity);
                String message = new String(responseContent);
                result = message.substring(1, message.length() - 1).replace("\\\"","'");
//                result=getCodeAndResult(response);
            }else if (code == 204){
                log.info("204无数据！");
            } else {
                log.info("请求失败"+code);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }
    /**
     * json格式提交参数-rta专用
     * @param uri 接口地址
     * @param params 参数
     * @param
     * @return
     * @throws IOException
     */
    public static String doWbRTAJsonPost(String uri, String params, TzBidRequest request){
        long time = System.currentTimeMillis();
        // 创建一个post请求
        HttpPost post = new HttpPost(uri);
        post.setHeader("accept", "*/*");
        post.setHeader("connection", "Keep-Alive");
        post.setHeader("Content-Type", "application/json");
        post.setHeader("apid", request.getAdv().getApp_id());
        post.setHeader("timeStamp", time+"");
        //设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(500).setConnectionRequestTimeout(200)
                .setSocketTimeout(500).build();
        post.setConfig(requestConfig);

        String result=null;
        String token = MD5Util.getMD5(request.getAdv().getApp_id() + request.getAdv().getBundle() + time);
        //设置参数
        if(!StringUtils.isBlank(params)){post.setEntity(new StringEntity(params, "utf-8"));}
        //设置
        if(!StringUtils.isBlank(token)){post.setHeader("token",token);}
        //创建客户端
        HttpClient httpClient = HttpClients.createDefault();

        //发送请求
        try {
            HttpResponse response = httpClient.execute(post);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200){
                log.info("请求成功"+code);
                result=getCodeAndResult(response);
            }else if (code == 204){
                log.info("204无数据！");
            } else {
                log.info("请求失败"+code);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }


    public static String doJsonsPost(String uri, String params,String ua ,String key ,String DATE){

        // 创建一个post请求
        HttpPost post = new HttpPost(uri);
        post.setHeader("accept", "*/*");
        post.setHeader("connection", "Keep-Alive");
        post.setHeader("Content-Type", "application/json;charset=UTF-8");
        if(!StringUtils.isBlank(ua)){
            post.setHeader("User-Agent",ua);
        }
        post.setHeader("Authorization", key);//星云Authorization请求头加密
        post.setHeader("P-Date", DATE);//星云请求头时间GMT格式
        //设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(500).setConnectionRequestTimeout(200)
                .setSocketTimeout(500).build();
        post.setConfig(requestConfig);

        String result=null;

        //设置参数
        if(!StringUtils.isBlank(params)){post.setEntity(new StringEntity(params, "utf-8"));}

        //创建客户端
        HttpClient httpClient = HttpClients.createDefault();

        //发送请求
        try {
            HttpResponse response = httpClient.execute(post);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200){
                log.info("请求成功"+code);
                result=getCodeAndResult(response);
            }else if (code == 204){
                log.info("204无数据！");
            } else {
                log.info("请求失败"+code);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }
    /**
     * json格式提交参数
     * @param uri 接口地址
     * @param params 参数
     * @param ua
     * @return
     * @throws IOException
     */
    public static String doInvenoJsonPost(String uri, String params,String ua){

        // 创建一个post请求
        HttpPost post = new HttpPost(uri);
        post.setHeader("accept", "*/*");
        post.setHeader("connection", "Keep-Alive");
        post.setHeader("Content-Type", "application/json;charset=UTF-8");
        post.setHeader("Accept-Encoding", "utf-8");
        //设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(500).setConnectionRequestTimeout(200)
                .setSocketTimeout(500).build();
        post.setConfig(requestConfig);

        if(!StringUtils.isBlank(ua)){
            post.setHeader("User-Agent",ua);
        }
        String result=null;

        //设置参数
        if(!StringUtils.isBlank(params)){post.setEntity(new StringEntity(params, "utf-8"));}

        //创建客户端
        HttpClient httpClient = HttpClients.createDefault();

        //发送请求
        try {
            HttpResponse response = httpClient.execute(post);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200){
                log.info("请求成功"+code);
                result=getCodeAndResult(response);
            }else if (code == 204){
                log.info("204无数据！");
            } else {
                log.info("请求失败"+code);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }
    /**
     * json格式提交参数
     * @param uri 接口地址
     * @param params 参数
     * @param ua
     * @return
     * @throws IOException
     */
    public static String doOnwWayJsonPost(String uri, String params,String ua){

        // 创建一个post请求
        HttpPost post = new HttpPost(uri);
        post.setHeader("accept", "*/*");
        post.setHeader("connection", "Keep-Alive");
        post.setHeader("Content-Type", "application/json;charset=UTF-8");
        post.setHeader("Accept-Encoding", "gzip");
        //设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(500).setConnectionRequestTimeout(200)
                .setSocketTimeout(500).build();
        post.setConfig(requestConfig);

        if(!StringUtils.isBlank(ua)){
            post.setHeader("User-Agent",ua);
        }
        String result=null;

        //设置参数
        if(!StringUtils.isBlank(params)){post.setEntity(new StringEntity(params, "utf-8"));}

        //创建客户端
        HttpClient httpClient = HttpClients.createDefault();

        //发送请求
        try {
            HttpResponse response = httpClient.execute(post);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200){
                log.info("请求成功"+code);
                result=getCodeAndResult(response);
            }else if (code == 204){
                log.info("204无数据！");
            } else {
                log.info("请求失败"+code);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }
}
