package com.televisivo.model.dto.in;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioIn {
    
    private Long id;
    private String username;
    private String email;
}