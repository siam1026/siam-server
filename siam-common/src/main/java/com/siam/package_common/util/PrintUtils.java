package com.siam.package_common.util;

import com.alipay.api.domain.StudentInfo;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class PrintUtils implements Printable {

    private StudentInfo studentInfo;
    private HttpServletRequest request;

    public PrintUtils(HttpServletRequest request, StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
        this.request = request;
    }

    public PrintUtils() {
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (page > 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Default", Font.PLAIN, 20));
        g2d.drawString("786", 60, 20);
        g2d.drawString(" 自提", 60, 15);
        g2d.drawString("-------------------------------------", 7, 30);
        g2d.setFont(new Font("Default", Font.PLAIN, 12));
        g2d.drawString("份数：" +"1", 5, 45);
        g2d.drawString("商品名称：" + "奶茶", 5, 75);
        g2d.drawString("-------------------------------------", 7, 30);
        g2d.drawString("客户：" +"陈先生", 5, 45);
        g2d.drawString("手机尾号：" + "5433", 5, 45);
        g2d.drawString("-------------------------------------", 7, 30);
        g2d.drawImage(writeQrCodeContent(),50, 225,100,100, null);
        g2d.drawString("♥扫码关注官方微信 周周有福利♥", 50, 225);
        g2d.drawString("-------------------------------------", 7, 30);
        g2d.drawString("暹罗外卖", 50, 225);
        return PAGE_EXISTS;
    }


    /**
     *@author ruanhui
     *@date 2018/12/10
     *@description 生成二维码
     */
    public Image writeQrCodeContent()  {
        Image im = null ;
        try {
            //获取图片路径
            String filePath = request.getSession().getServletContext().getRealPath("/") + "resource/images/";
            System.out.println("filePath:" + filePath) ;
            File file=new File(filePath + "xxx.jpg");
            System.out.println(file.getName());
            InputStream is = new FileInputStream(file);
            BufferedImage bi;
            bi = ImageIO.read(is);
            im = (Image)bi;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return im;
    }

    //打印方法
    public static void print(HttpServletRequest request, StudentInfo studentInfo) {

        int height = 250 + 3 * 15 + 20;

        // 通俗理解就是书、文档
        Book book = new Book();

        // 打印格式
        PageFormat pf = new PageFormat();
        pf.setOrientation(PageFormat.PORTRAIT);

        // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
        Paper p = new Paper();
        p.setSize(230, height);
        p.setImageableArea(5, -20, 230, height + 20);
        pf.setPaper(p);

        // 把 PageFormat 和 Printable 添加到书中，组成一个页面
        book.append(new PrintUtils(request,studentInfo), pf);

        // 获取打印服务对象
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(book);
        try {
            job.print();
        } catch (PrinterException e) {
            System.out.println("================打印出现异常");
        }
    }
}
