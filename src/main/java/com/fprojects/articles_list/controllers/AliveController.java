package com.fprojects.articles_list.controllers;

import com.fprojects.articles_list.services.EMailService;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Check is application start up
 */
@RestController
public class AliveController {

    private final EMailService eMailService;

    @Autowired
    public AliveController(EMailService eMailService) {
        this.eMailService = eMailService;
    }

    @GetMapping(path = "/")
    public String appWorkingAnswer(@RequestParam("file") MultipartFile file) throws IOException, MessagingException {

        return "Articles List works!";
    }

    @GetMapping(path = "/send")
    public String sendEmail(@RequestParam("file") MultipartFile file) throws IOException, MessagingException {

        eMailService.sentMessageWithAttachment(file);
        return "Письмо успешно отправлено";
    }

    @GetMapping(path = "/receive")
    public String receiveEmails(@RequestParam("file") MultipartFile file) throws IOException, MessagingException {

        eMailService.receive();
        return "Доступ к почте получен";
    }
}
