package com.sncf.govi.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class DemandeGOVI {
    private List<String> idGares;
    private LocalDateTime date;
}
