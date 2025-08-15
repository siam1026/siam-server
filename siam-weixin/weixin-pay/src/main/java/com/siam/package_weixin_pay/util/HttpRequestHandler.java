package com.siam.package_weixin_pay.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;

import com.siam.package_weixin_pay.entity.TransfersDto;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;
/*import org.apache.commons.cli.oss.KeyStores;*/

public class HttpRequestHandler
{

    // 连接超时时间，默认10秒
    private int socketTimeout = 10000;

    // 传输超时时间，默认30秒
    private int connectTimeout = 30000;

    // 请求器的配置
    private static RequestConfig requestConfig;

    // HTTP请求器
    private static CloseableHttpClient httpClient;

    /**
     * 加载证书
     *
     * @param path
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    /*private static void initCert(String path, TransfersDto transfer)
        throws IOException, KeyStoreException, UnrecoverableKeyException,
        NoSuchAlgorithmException, KeyManagementException
    {
        // 拼接证书的路径
        KeyStore keyStore = KeyStores.getInstance("PKCS12", path, transfer.map());

        // 加载本地的证书进行https加密传输
        FileInputStream instream = new FileInputStream(new File(path));
        try
        {
            keyStore.load(instream, transfer.getMchid().toCharArray()); // 加载证书密码，默认为商户ID
        }
        catch (CertificateException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        finally
        {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore,
            transfer.getMchid().toCharArray()).build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
            new String[] {"TLSv1"}, null,
            SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        // 根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(30000).build();

    }*/



    /**
     * 加载证书
     *
     */
    private static void initCert() throws Exception {
        // 证书密码，默认为商户ID
        String key = "1585884941";
        // 商户证书的路径
        /*String path = Constants.CERT_PATH;*/

        // 指定读取证书格式为PKCS12
        KeyStore keyStore = KeyStore.getInstance("pkcs12");

        // 读取本机存放的PKCS12证书文件
        /*FileInputStream instream = new FileInputStream(new File(path));*/
        ClassPathResource classPathResource = new ClassPathResource("wx_cert/apiclient_cert.p12");
        InputStream instream = classPathResource.getInputStream();

        try {
            // 指定PKCS12的密码(商户ID)
            keyStore.load(instream, key.toCharArray());
        } finally {
            instream.close();
        }

        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, key.toCharArray()).build();

        // 指定TLS版本
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, null, null,SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        // 设置httpclient的SSLSocketFactory
        httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }



    /**
     * 通过Https往API post xml数据
     *
     * @param url
     *            API地址
     * @param xmlObj
     *            要提交的XML数据对象
     * @param path
     *            当前目录，用于加载证书
     * @return
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static String httpsRequest(String url, String xmlObj, TransfersDto model, String path)
            throws Exception {
        // 加载证书
        initCert();

        String result = null;

        HttpPost httpPost = new HttpPost(url);

        // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(xmlObj, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        // 设置请求器的配置
        httpPost.setConfig(requestConfig);

        try
        {
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");

        }
        catch (ConnectionPoolTimeoutException e)
        {

        }
        catch (ConnectTimeoutException e)
        {

        }
        catch (SocketTimeoutException e)
        {

        }
        catch (Exception e)
        {

        }
        finally
        {
            httpPost.abort();
        }

        return result;
    }
}
