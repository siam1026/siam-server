package com.siam.package_common.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片合成服务类
 */
@Data
@Slf4j
@Component
public class ImageComposeUtils {

    @Autowired
    private OSSUtils ossUtils;

    /**
     * 图片合成
     * @param mainImagePath   主图片路径
     * @param qrCodePath    二维码路径
     * @param savePath  图片合成保存路径
     * @return
     */
    public void compoundImage(String mainImagePath, String qrCodePath, String savePath, String vipNo) throws IOException {
        HttpURLConnection httpUrl = (HttpURLConnection) new URL(mainImagePath).openConnection();
        httpUrl.connect();

        HttpURLConnection httpUrl_qrcode = (HttpURLConnection) new URL(qrCodePath).openConnection();
        httpUrl_qrcode.connect();

        try {
            //1、获取主图片
            BufferedImage big = ImageIO.read(httpUrl.getInputStream());//mainImagePath
            //2、拿到二维码
            BufferedImage erweima = ImageIO.read(httpUrl_qrcode.getInputStream());//qrCodePath
            int width = 1080;
            int height = 1854;
            Image image = big.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage bufferedImage2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            //3、开始绘图
            Graphics2D g = bufferedImage2.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.drawImage(erweima, 320, 910, 460, 460, null);
            Font font = new Font("微软雅黑", Font.BOLD, 38);
            g.setFont(font);
            g.setPaint(Color.BLACK);
            //4、设置位置
            String text = vipNo; //合成图片上需要显示的文字
            int wordWidth = getWordWidth(font, text);
            int i = width / 2;
            int i1 = (i - wordWidth) / 2;
            int numWidth=i+i1;
//            g.drawString(text, numWidth-10, 310+28);
            g.drawString(text, 350, 850);
            g.dispose();

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage2, "png", os);
            InputStream inputStream = new ByteArrayInputStream(os.toByteArray());

            //将太阳码上传到oss服务器上
            ossUtils.uploadImage(inputStream, savePath);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            httpUrl.disconnect();
            httpUrl_qrcode.disconnect();
        }
    }

    public int getWordWidth(Font font, String content) {
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
    public File inputStreamToFile(InputStream ins, String name) throws Exception{
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

}