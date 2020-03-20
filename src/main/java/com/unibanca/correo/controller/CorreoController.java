package com.unibanca.correo.controller;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.unibanca.correo.dto.CorreoDto;
import com.unibanca.correo.service.EnvioCorreoService;

@RestController
public class CorreoController {

	@Autowired
	EnvioCorreoService email;

	@PostMapping("/mail")
	public ResponseEntity<String> sendEmailFile(@RequestParam("to") String to, @RequestParam("subject") String subject,
			@RequestParam("text") String text, @RequestParam("file") MultipartFile file) throws IOException {

		CorreoDto correoDto = new CorreoDto();
		correoDto.setTo(to);
		correoDto.setSubject(subject);
		correoDto.setText(text);

		if (file == null || file.isEmpty()) {
			email.sendSimpleMail(correoDto);
			return new ResponseEntity<String>("Ok", HttpStatus.OK);
		} else {
			try {
				email.SendMailWithFile(correoDto, file);
			} catch (MessagingException e) {
				return new ResponseEntity<String>("Error", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<String>("Ok", HttpStatus.OK);
		}
	}
	
	@GetMapping("/test")
	public String test() {
		return "ok";
	}

}
