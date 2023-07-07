package com.sncf.govi.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class DemandeGOVI {
    private List<String> idGares;
    private LocalDateTime date;
}
