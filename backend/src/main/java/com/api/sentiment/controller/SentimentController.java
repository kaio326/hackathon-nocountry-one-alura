package com.api.sentiment.controller;

import com.api.sentiment.dto.SentimentRequest;
import com.api.sentiment.dto.SentimentResponse;
import com.api.sentiment.service.SentimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/sentiment")
@CrossOrigin(origins = "*")
public class SentimentController {

    @Autowired
    private SentimentService sentimentService;

    /**
     * Endpoint para análise de sentimento de um texto único
     * 
     * @param request Contém o texto para análise
     * @return SentimentResponse com previsão e probabilidade
     */
    @PostMapping("/predict")
    public ResponseEntity<SentimentResponse> predictSentiment(
            @Valid @RequestBody SentimentRequest request) {
        
        log.info("Requisição recebida para análise de sentimento");
        log.debug("Texto: {}", request.getText().substring(0, Math.min(50, request.getText().length())));
        
        try {
            // Validação
            if (request.getText() == null || request.getText().trim().isEmpty()) {
                log.warn("Texto vazio recebido");
                return ResponseEntity.badRequest()
                    .body(new SentimentResponse("Erro", 0.0, "Texto não pode estar vazio"));
            }

            if (request.getText().length() < 3) {
                log.warn("Texto muito curto: {} caracteres", request.getText().length());
                return ResponseEntity.badRequest()
                    .body(new SentimentResponse("Erro", 0.0, "Texto deve ter no mínimo 3 caracteres"));
            }

            // Chamar serviço
            SentimentResponse response = sentimentService.predictSentiment(request.getText());
            
            log.info("Sentimento predito: {} (probabilidade: {})", 
                response.getPrevisao(), response.getProbabilidade());
            
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Erro ao processar requisição: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                .body(new SentimentResponse("Erro", 0.0, "Erro ao processar: " + e.getMessage()));
        }
    }

    /**
     * Health check da API
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        log.info("Health check realizado");
        return ResponseEntity.ok("API Sentiment Analysis está rodando!");
    }
}
