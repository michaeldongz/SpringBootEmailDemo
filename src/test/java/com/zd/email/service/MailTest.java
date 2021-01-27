package com.zd.email.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author zhangdong
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

    @Resource
    private MailService mailService;
    @Resource
    private TemplateEngine templateEngine;

    @Test
    public void sayHelloTest(){
        mailService.sayHello();
    }

    /**
     * 普通文本邮件 - test
     */
    @Test
    public void sendSimpleEmailTest(){
        String to = "841897775@qq.com";
        String subject = "简单的文本邮件Subject";
        String content = "简单的文本邮件ContentContentContentContentContent";
        mailService.sendSimpleEmail(to, subject, content);
    }

    /**
     * html邮件 - test
     * @throws MessagingException
     */
    @Test
    public void sendHtmlEmailTest() throws MessagingException {
        String to = "841897775@qq.com";
        String subject = "简单的Html文本邮件Subject";
//        String content = "<html>\n"+
//                            "<body>\n"+
//                            "<h1>Hello</h1><h4>ha<font color='red'>hahaha</font>ha</h4>\n"+
//                            "</body>\n"+
//                          "</html>";
        String content = "<html><body><h1>Hello</h1><h4>ha<font color='red'>hahaha</font>ha</h4></body></html>";
        mailService.sendHtmlEmail(to, subject, content);
    }

    /**
     * 附件邮件 - test
     * @throws MessagingException
     */
    @Test
    public void sendAttachmentsEmailTest() throws MessagingException {
        String to = "841897775@qq.com";
        String subject = "简单的附件文本邮件Subject";
        String content = "附件content内容";
        String filePath = "C:\\Users\\Administrator\\Desktop\\测试资料\\mp4\\mov_bbb.mp4";
        mailService.sendAttachmentsEmail(to, subject, content,filePath);
    }

    /**
     * 图片邮件 - test
     * @throws MessagingException
     */
    @Test
    public void sendPictureEmailTest() throws MessagingException {
        String to = "841897775@qq.com";
        String subject = "简单的图片邮件Subject";
        String filePath = "C:\\Users\\Administrator\\Desktop\\测试资料\\pic\\2.jpg";
        String id = "michael1";
        String content = "<html><body><h1>图片：</h1><img src=/'cid:"+id+"/'></img></body></html>";
        mailService.sendPictureEmail(to, subject, content,filePath,id);
    }

    @Test
    public void templateEmailTest() throws MessagingException {
        Context context = new Context();
        context.setVariable("id","1001");

        String emailContent= templateEngine.process("emailTemplate",context);

        String to = "841897775@qq.com";
        String subject = "Html-模板邮件Subject";
        mailService.sendHtmlEmail(to, subject, emailContent);
    }
}
