package com.api.sentiment.service;

import com.api.sentiment.dto.SentimentResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SentimentService {

    @Value("${sentiment.api.url:http://localhost:5000}")
    private String sentimentApiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SentimentService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Chama o microserviço Python para predição de sentimento
     */
    public SentimentResponse predictSentiment(String text) {
        try {
            // Preparar requisição
            String url = sentimentApiUrl + "/predict";
            String requestBody = "{\"text\": \"" + escapeJson(text) + "\"}";
            
            log.info("Chamando microserviço Python: {}", url);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
            
            // Fazer chamada
            String response = restTemplate.postForObject(url, request, String.class);
            
            // Parsear resposta
            JsonNode jsonNode = objectMapper.readTree(response);
            
            String previsao = jsonNode.get("previsao").asText();
            Double probabilidade = jsonNode.get("probabilidade").asDouble();
            
            log.info("Resposta recebida: {} com probabilidade {}", previsao, probabilidade);
            
            return new SentimentResponse(
                previsao,
                probabilidade,
                "Análise concluída com sucesso"
            );

        } catch (Exception e) {
            log.error("Erro ao chamar microserviço Python: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao processar sentimento: " + e.getMessage(), e);
        }
    }

    /**
     * Escapa caracteres especiais para JSON
     */
    private String escapeJson(String text) {
        return text
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
    }
}
