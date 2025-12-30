package com.api.sentiment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SentimentRequest {
    
    @NotBlank(message = "Texto não pode estar vazio")
    @Size(min = 3, max = 5000, message = "Texto deve ter entre 3 e 5000 caracteres")
    private String text;
}
