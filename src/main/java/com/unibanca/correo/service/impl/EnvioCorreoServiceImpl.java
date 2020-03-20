package com.unibanca.correo.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.unibanca.correo.dto.CorreoDto;
import com.unibanca.correo.service.EnvioCorreoService;

@Service
public class EnvioCorreoServiceImpl implements EnvioCorreoService {

	private static String UPLOADED_FOLDER = "D://temp//";

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void SendMailWithFile(CorreoDto correoDto, MultipartFile file) throws MessagingException, IOException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper email = new MimeMessageHelper(message, true);

		try {
			email.setTo(correoDto.getTo());
			email.setSubject(correoDto.getSubject());
			email.setText(correoDto.getText());

			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);

			FileSystemResource newFile = new FileSystemResource(new File(UPLOADED_FOLDER + file.getOriginalFilename()));

			email.addAttachment(file.getOriginalFilename(), newFile);

			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sendSimpleMail(CorreoDto correoDto) {

		SimpleMailMessage email = new SimpleMailMessage();

		email.setTo(correoDto.getTo());
		email.setSubject(correoDto.getSubject());
		email.setText(correoDto.getText());

		mailSender.send(email);

	}

}
