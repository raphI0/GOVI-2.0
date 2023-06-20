package com.sncf.govi.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FichierGOVI {
    private TypeFichierEnum typeFichier;
    private MultipartFile multipartFile;
}
