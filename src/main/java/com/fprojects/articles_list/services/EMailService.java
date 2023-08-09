package com.fprojects.articles_list.services;

import com.sun.mail.util.BASE64DecoderStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import javax.mail.util.ByteArrayDataSource;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
public class EMailService {

    private final JavaMailSender emailSender;

    @Autowired
    public EMailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEMail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("fedinivanvladimirovich@mail.ru");
        simpleMailMessage.setTo("fedin@vsk.ru");
        simpleMailMessage.setCc("fedinivanvlad@gmail.com");
        simpleMailMessage.setCc("fedinivanvladimirovich@mail.ru");
        simpleMailMessage.setSubject("Заголовок письма");
        simpleMailMessage.setText("Тело письма: такие то извещения не получены бла бла бла");

        emailSender.send(simpleMailMessage);
    }

    public void sentMessageWithAttachment(MultipartFile file) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        ByteArrayDataSource attachment = new ByteArrayDataSource(file.getBytes(), "application/octet-stream");

        helper.setFrom("fedinivanvladimirovich@mail.ru");
        helper.setTo("fedinivanvladimirovich@mail.ru");
        helper.setSubject("Заголовок для тестового письма");
        helper.addAttachment(file.getOriginalFilename(), attachment);
        helper.setText("", true);
        emailSender.send(message);
    }

    public void receive() throws MessagingException, IOException {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imap"); // Например, "imap" для IMAP, "pop3" для POP3
        properties.put("mail.imap.host", "imap.mail.ru");
        properties.put("mail.imap.port", 993);
        properties.put("mail.imap.ssl.enable", "true"); // Если требуется SSL
        properties.put("mail.imap.auth", "true"); // Если требуется аутентификация

        // Создание сессии
        Session session = Session.getDefaultInstance(properties);

        // Получение почтового хранилища
        Store store = session.getStore();
        store.connect("fedinivanvladimirovich@mail.ru", "0evxhx2pKjtyrJ9Rmx7d");

        // Открытие папки с письмами
        Folder folder = store.getFolder("inbox");
        folder.open(Folder.READ_ONLY);

        Flags seen = new Flags(Flags.Flag.SEEN);
        FlagTerm unseenFlagTerm = new FlagTerm(seen, false);

        // Получение писем
        Message[] messages = folder.search(unseenFlagTerm);
        for (Message message : messages) {


            // Если письмо является объектом MimeMessage, можно привести его к этому типу для работы с дополнительной информацией
            if (message.isMimeType("multipart/*")) {
                Multipart multipart = (Multipart) message.getContent();
                // Обработка письма, например, вывод его содержимого
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent());

                // Получение количества частей сообщения
                int count = multipart.getCount();
                for (int i = 0; i < count; i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);

                    // Проверка, является ли часть вложением
                    if (bodyPartDispositionIsAttachment(bodyPart)) {
                        MimeBodyPart attachmentPart = (MimeBodyPart) bodyPart;
                        String fileName = bodyPart.getFileName();
                        System.out.println("Название прикреплённого файла: " + fileName);
                        Object content = bodyPart.getContent();

                        InputStream inputStream = attachmentPart.getInputStream();
                        byte[] attachmentData = new byte[inputStream.available()];
                        inputStream.read(attachmentData);

                        byte[] decodedData = BASE64DecoderStream.decode(attachmentData);



                    }
                }
            }
        }

        // Закрытие папки и хранилища
        folder.close(false);
        store.close();
    }

    public static boolean bodyPartDispositionIsAttachment(BodyPart bodyPart) throws MessagingException {
        String disposition = bodyPart.getDisposition();
        return disposition != null && (disposition.equalsIgnoreCase(Part.ATTACHMENT) || disposition.equalsIgnoreCase(Part.INLINE));
    }

}
