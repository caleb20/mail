package com.unibanca.correo.service;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.web.multipart.MultipartFile;

import com.unibanca.correo.dto.CorreoDto;

public interface EnvioCorreoService {

	public void SendMailWithFile(CorreoDto correoDto, MultipartFile file) throws MessagingException, IOException;

	public void sendSimpleMail(CorreoDto correoDto);

}
