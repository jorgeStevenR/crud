package com.example.crud.services;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.example.crud.model.Persona;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private TemplateEngine templateEngine;
    
    @Autowired
    private PersonaServices personaServices;

    public void enviarPdfPorEmail(Long id, String destinatario) throws Exception {
        // Obtener persona
        Persona persona = personaServices.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        // Generar PDF
        byte[] pdfBytes = generarPdf(persona);
        
        // Configurar email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setTo(destinatario);
        helper.setSubject("Detalle de Usuario - " + persona.getNombre());
        helper.setText("Adjunto encontrará los detalles del usuario.");
        
        // Adjuntar PDF
        helper.addAttachment("usuario.pdf", new ByteArrayResource(pdfBytes));
        
        // Enviar
        mailSender.send(message);
    }
    
    private byte[] generarPdf(Persona persona) throws Exception {
        Context context = new Context();
        context.setVariable("persona", persona);
        
        String htmlContent = templateEngine.process("pdf-template", context);
        
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        renderer.createPDF(outputStream);
        outputStream.close();
        
        return outputStream.toByteArray();
    }
}