package com.siam.system.modular.package_goods;

import com.alibaba.fastjson.JSONObject;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ImageComposeTest {

    public static void main(String[] args) throws IOException {
        CompoundImage("https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/test/siamCompose.png", "https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/test/eb831e8f0b42b8b3a8b4360c11a2b5e.jpg", "", "C:\\Users\\Administrator\\Desktop\\testComponse.png", "postion");

        /*try {
            String destUrl = "https://ibsbjstar.ccb.com.cn/NCCB_Encoder/Encoder?CODE=EUSo4Twu6YRk2JDPJ9OnpfgrdDObpGwKBGOTqVwnBfWAvjCP5UOFuig6hySmrww2hXVIqDgb5NUOrvwI5NRuqtQ3pXcRVh";
            HttpURLConnection httpUrl = (HttpURLConnection) new URL(destUrl).openConnection();
            httpUrl.connect();
            File file = inputStreamToFile(httpUrl.getInputStream(),"url.png");
            System.out.println("111====>>>>"+file.getPath());
            httpUrl.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }*/


//        qrcode();
    }


    /**
     * 图片合成
     * @param mainImgPath   主图片路径
     * @param qrcodePath    二维码路径
     * @param text          文字
     * @param compoundPath  图片合成保存路径
     * @param databasePath  数据库保存路径
     * @return  保存路径
     */
    public static String CompoundImage(String mainImgPath, String qrcodePath,String text,String compoundPath,String databasePath) throws IOException {
        String destUrl = mainImgPath;
        HttpURLConnection httpUrl = (HttpURLConnection) new URL(destUrl).openConnection();
        httpUrl.connect();

        String destUrl_qrcode = "https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/test/testQrcode.png";
        HttpURLConnection httpUrl_qrcode = (HttpURLConnection) new URL(destUrl_qrcode).openConnection();
        httpUrl_qrcode.connect();

        String ewmurl="";
        try {
            //1、获取主图片
            BufferedImage big = ImageIO.read(httpUrl.getInputStream());//mainImgPath
            URL url = new URL("https://img-blog.csdn.net/20150906104118760");
            //2、拿到二维码
            BufferedImage erweima = ImageIO.read(httpUrl_qrcode.getInputStream());//qrcodePath
            int width = 1080;
            int height = 1854;
            Image image = big.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage bufferedImage2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            //3、开始绘图
            Graphics2D g = bufferedImage2.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.drawImage(erweima, 20, 1500, 300, 300, null);
            Font font = new Font("微软雅黑", Font.BOLD, 38);
            g.setFont(font);
            g.setPaint(Color.BLACK);
            //4、设置位置
            int wordWidth = getWordWidth(font, text);
            int i = width / 2;
            int i1 = (i - wordWidth) / 2;
            int numWidth=i+i1;
            g.drawString(text, numWidth-10, 310+28);
            g.dispose();

            ewmurl =compoundPath;
            ImageIO.write(bufferedImage2, "jpg", new File(ewmurl));
            System.out.println("图片生成完毕");
            ewmurl = databasePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpUrl.disconnect();
        httpUrl_qrcode.disconnect();
        return ewmurl;
    }


//    /**
//     * 生成二维码
//     * @param backLink  扫码回调链接
//     * @param savePath  二维码保存路径
//     * @return 返回保存路径（用于保存数据库）
//     * @throws IOException
//     */
//    public static String createQRCode(String backLink,String savePath) throws IOException {
//        //计算二维码图片的高宽比
//        int v = 6;
//        int width = 67 + 12 * (v - 1);
//        int height = 67 + 12 * (v - 1);
//
//        Map x = new HashMap();
//        x.put("setQrcodeErrorCorrect", 'L');
//        x.put("setQrcodeEncodeMode", 'B');//注意版本信息 N代表数字 、A代表 a-z,A-Z、B代表 其他)
//        x.put("setQrcodeVersion", v);
//
//        byte[] d = backLink.getBytes("utf-8");//汉字转格式需要抛出异常
//
//        //缓冲区
//        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
//
//        //绘图
//        Graphics2D gs = bufferedImage.createGraphics();
//
//        gs.setBackground(Color.WHITE);
//        gs.setColor(Color.BLACK);
//        gs.clearRect(0, 0, width, height);
//
//        //偏移量
//        int pixoff = 2;
//
//        if (d.length > 0 && d.length < 120) {
//            boolean[][] s = x.calQrcode(d);
//
//            for (int i = 0; i < s.length; i++) {
//                for (int j = 0; j < s.length; j++) {
//                    if (s[j][i]) {
//                        gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
//                    }
//                }
//            }
//        }
//        gs.dispose();
//        bufferedImage.flush();
//        //设置图片格式，与输出的路径
//        String url = savePath;
//        ImageIO.write(bufferedImage, "png", new File(url));
//        System.out.println("二维码生成完毕");
//        return url;
//    }

    public static int getWordWidth(Font font, String content) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        int width = 0;
        for (int i = 0; i < content.length(); i++) {
            width += metrics.charWidth(content.charAt(i));
        }
        return width;
    }








    /**
     *   工具类
     * inputStream 转 File
     */
    public static File inputStreamToFile(InputStream ins, String name) throws Exception{
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + name);
        if (file.exists()) {
            return file;
        }
        /*OutputStream os = new FileOutputStream(file);
        int bytesRead;
        int len = 8192;
        byte[] buffer = new byte[len];
        while ((bytesRead = ins.read(buffer, 0, len)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();*/
        ins.close();
        return file;
    }











    public static void qrcode() {
        try {
            URL url = new URL("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + getToken());
            System.out.println(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", "inviterId=111");//参数
            paramJson.put("page", "pages/login/choose/choose");//小程序未发布前注释，否则找不到对应的页面，就会生成错误的图片
            paramJson.put("width", 430);
//            paramJson.put("auto_color", true);
//            paramJson.put("auto_color", false);
            JSONObject lineColor = new JSONObject();
            lineColor.put("r", 0);
            lineColor.put("g", 0);
            lineColor.put("b", 0);
            paramJson.put("line_color", lineColor);
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            OutputStream os = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\testQrcode.png"));
            BufferedImage read = ImageIO.read(httpURLConnection.getInputStream());
            if (read == null) {
                System.out.println("null");
            } else {
                ImageIO.write(read, "png", new File("C:\\Users\\Administrator\\Desktop\\testQrcode.png"));
            }
            int len;
            byte[] arr = new byte[1024];
            while ((len = bis.read(arr)) != -1) {
                os.write(arr, 0, len);
                os.flush();
            }
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, ?> paramMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String param = "";
        Iterator<String> it = paramMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            param += key + "=" + paramMap.get(key) + "&";
        }
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String getToken() {
        try {
            Map<String, String> map = new LinkedHashMap<String, String>();
            map.put("grant_type", "client_credential");
            map.put("appid", "wx2e1a8193d3ed12fe");// 这里我是写在配置类里，需要改成自己的appid
            map.put("secret", "2774e3a86dc30fbf1ac63d81b56f2291"); //这里我是写在配置类里，需要改成自己的secret
            String rt = sendPost("https://api.weixin.qq.com/cgi-bin/token", map);
            JSONObject json = JSONObject.parseObject(rt);
            if (json.getString("access_token") != null || json.getString("access_token") != "") {
//                System.out.println("token:" + json.getString("access_token"));
                return json.getString("access_token");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}