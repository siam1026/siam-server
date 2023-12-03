package com.siam.package_common.service;

import com.siam.package_common.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Service
public class EmailService {

    @Value("${spring.mail.username:siam1026@163.com}")
    private String fromAddress;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    /**
     * 发送简单邮件
     */
    public void sendMail(String toAddress, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(text);
        message.setTo(toAddress);
        message.setFrom(fromAddress);
        javaMailSender.send(message);
    }

    /**
     * 发送复杂邮件 html内容或带附件
     */
    public void sendMail(String toAddress, String subject, String text, String fileUrl, String fileName) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setSubject(subject);

            // 发送html格式内容
            helper.setText(text,true);
            helper.setTo(toAddress);
            helper.setFrom(fromAddress);

            //添加附件
            HttpURLConnection httpUrl = (HttpURLConnection) new URL(fileUrl).openConnection();
            httpUrl.connect();
            File file = ToolUtil.inputStreamToFile(httpUrl.getInputStream(), fileName);
            helper.addAttachment(fileName, file);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }

//    /**
//     * 使用加密的方式,利用465端口进行传输邮件,开启ssl
//     * user对象为接收邮件的一些信息
//     */
//    public void sendMail(String toAddress, String subject, String text, String fileUrl, String fileName) {
//        try {
//            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
//            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
//            // 设置邮件会话参数
//            Properties props = new Properties();
//            // 邮箱的发送服务器地址
//            props.setProperty("mail.smtp.host", "smtp.126.com");
//            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
//            props.setProperty("mail.smtp.socketFactory.fallback", "false");
//            // 邮箱发送服务器端口,这里设置为465端口
//            props.setProperty("mail.smtp.port", "465");
//            props.setProperty("mail.smtp.socketFactory.port", "465");
//            props.put("mail.smtp.auth", "true");
//            final String username = "siam1026@163.com";//这里就是配置文件中定义的用户名
//            final String password = "WCQLCXYKFVJOBXCZ";
//            // 获取到邮箱会话,利用匿名内部类的方式,将发送者邮箱用户名和密码授权给jvm
//            Session session = Session.getDefaultInstance(props, new Authenticator() {
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(username, password);
//                }
//            });
//            // 通过会话,得到一个邮件,用于发送
//            Message msg = new MimeMessage(session);
//            // 设置发件人
//            msg.setFrom(new InternetAddress("siam1026@163.com"));
//            // 设置收件人,to为收件人,cc为抄送,bcc为密送
//            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress, false));
//            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(toAddress, false));
//            msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(toAddress, false));
//            msg.setSubject("xx邮件");
//            // 设置邮件消息
//            msg.setText("恭喜你，此邮件。。。。。。。。不需要回复");//这里也可以定义跳转链接
//            // 设置发送的日期
//            msg.setSentDate(new Date());
//
//            // 调用Transport的send方法去发送邮件
//            Transport.send(msg);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}