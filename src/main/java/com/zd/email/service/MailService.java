package com.zd.email.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author zhangdong
 */
@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sayHello(){
        System.out.println("Hi, SpringBoot email world");
    }

    /**
     * 普通文本邮件
     * @param to        收件人邮箱
     * @param subject   邮件主题
     * @param content   邮件内容
     */
    public void sendSimpleEmail(String to,String subject,String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(from);
        javaMailSender.send(message);
    }

    /**
     * Html邮件
     * @param to        收件人邮箱
     * @param subject   邮件主题
     * @param content   邮件内容
     */
    public void sendHtmlEmail(String to,String subject,String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setFrom(from);
        javaMailSender.send(message);
    }

    /**
     * 附件邮件
     * @param to        收件人邮箱
     * @param subject   邮件主题
     * @param content   邮件内容
     * @param filePath  附件路径
     */
    public void sendAttachmentsEmail(String to,String subject,String content,String filePath) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setFrom(from);

        FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
        String fileName = fileSystemResource.getFilename();
        helper.addAttachment(fileName, fileSystemResource);
        helper.addAttachment(fileName, fileSystemResource);

        javaMailSender.send(message);
    }

    /**
     * 图片邮件
     * @param to        收件人邮箱
     * @param subject   邮件主题
     * @param content   邮件内容
     * @param filePath  附件路径
     */
    public void sendPictureEmail(String to,String subject,String content,String filePath,String id) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setFrom(from);

        FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
        helper.addInline(id, fileSystemResource);

        javaMailSender.send(message);
    }
}
