package com.saosao;

import com.google.common.collect.Sets;
import javafx.beans.binding.When;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

/**
 * @Author: liyang117
 * @Date: 2018/4/19 15:38
 * @Description:
 */
public class SMTPUtils {
    // 默认的编码方式
    private static final String MAIL_DEFAULT_CHARSET = "UTF-8";
    // 默认的连接等待时间（ms）
    private static final int MAIL_DEFAULT_CONNECTION_TIMEOUT = 5000;
    // 默认的数据等待时间（ms）
    private static final int MAIL_DEFAULT_SOCKET_TIMEOUT = 30000;

    // host="smtp.meituan.com"
    private String host = "smtp.meituan.com";
    // port="587"
    private int port = 587;
    // 发件人邮箱 sender 必须与 senderId 对应
    private String sender = "liyang117";
    // 发件人姓名
    private String senderName;
    // MIS 帐号，例如：guoning02
// Note：如果公司从美团拆出去的话，试试 senderId = "guoning02@maoyan.com";
    private String senderId = "liyang117";
    // MIS 密码
    private String senderPassword = "Zhouniao666*";
    // 收件人
    private Set<String> defaultRecipient;

    private boolean send(String sender, String senderName, String title, String msg, Set<String> recipients, EmailAttachment attachment , String html) {
        // 发件人不能为空
        if (StringUtils.isBlank(sender)) {
            System.err.println(String.format("发件人为空，邮件发送失败；邮件主题：%s，发件人：%s，发件人姓名：%s，收件人：%s，邮件内容：%s", title, sender, senderName, recipients, msg));
            return false;
        }
        // 邮件主题不能为空
        if (StringUtils.isBlank(title)) {
            System.err.println(String.format("邮件主题为空，邮件发送失败；邮件主题：%s，发件人：%s，发件人姓名：%s，收件人：%s，邮件内容：%s", title, sender, senderName, recipients, msg));
            return false;
        }
        // 收件人不能为空
        if (CollectionUtils.isEmpty(recipients)) {
            System.err.println(String.format("收件人为空，邮件发送失败；邮件主题：%s，发件人：%s，发件人姓名：%s，收件人：%s，邮件内容：%s", title, sender, senderName, recipients, msg));
            return false;
        }

        HtmlEmail email = new HtmlEmail();
        email.setCharset(MAIL_DEFAULT_CHARSET);
        email.setHostName(host);
        email.setSmtpPort(port);
        email.setSocketConnectionTimeout(MAIL_DEFAULT_CONNECTION_TIMEOUT);
        email.setSocketTimeout(MAIL_DEFAULT_SOCKET_TIMEOUT);

        try {
            for (String recipient : recipients) {
                email.addTo(recipient);
            }
            email.setAuthentication(senderId, senderPassword);
            email.setFrom(sender, senderName);
            email.setSubject(title);
            email.setHtmlMsg(html) ;
            if (null != attachment) {
                email.attach(attachment);
            }
            email.send();
        } catch (EmailException e) {
            System.err.println(String.format("邮件发送失败，邮件主题：%s，发件人：%s，发件人姓名：%s，收件人：%s，部分邮件内容：%s，异常信息：%s, %s, %s", title, sender, senderName, recipients,
                    StringUtils.trimToEmpty(msg), e.getMessage(), e.getCause(), e.getCause().getCause()));
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
//        String html = "<!DOCTYPE html>";
//        html += "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">";
//        html += "<title>Insert title here</title>";
//        html += "</head><body>";
//        html += "<div style=\"width:600px;height:400px;margin:50px auto;\">";
//        html += "<h1>我来看看邮件是否发送成功呢</h1><br/><br/>";
//        html += "<p>下面是通过该协议可以创建一个指向电子邮件地址的超级链接，通过该链接可以在Internet中发送电子邮件</p><br/>";
//        html += "<a href=\"mailto:huntereagle@foxmail.com?subject=test&cc=huntertochen@163.com&body=use mailto sample\">send mail</a>";
//        html += "</div>";
//        html += "</body></html>";
//        System.out.println(html);
        String html = "" ;
        try {
            Scanner in = new Scanner(new File("/Users/liyang/IdeaProjects103/gjk/src/main/webapp/view/mail.html")) ;
            while (in.hasNext()){
                html += in.nextLine() + "\n" ;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(html);

        SMTPUtils smtpUtils = new SMTPUtils() ;
        smtpUtils.send("liyang117@meituan.com" , "李洋","测试" ,"hello" , Sets.newHashSet("liyang117@meituan.com" , "dean.yan@dianping.com") , null , html) ;
    }
}
