# Backend simples para resolver as chamadas a API

## Terminal 1: Ir até o diretório de data_science e rodar microserviço Python
```
cd data_science
python sentiment_api.py
```
## Terminal 2: Ir até o dirétorio do backend e rodar Spring Boot
```
cd backend
mvn spring-boot:run
```
# Terminal 3: Testar a API
```
curl -X POST http://localhost:8080/api/sentiment/predict \
  -H "Content-Type: application/json" \
  -d '{"text": "Este produto é excelente!"}'
```