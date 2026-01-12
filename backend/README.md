# Backend - Java Spring Boot

**Para setup completo com Docker**, veja [README raiz](../README.md).

## 🚀 Como rodar

### Via Docker (Recomendado)
```bash
sudo docker-compose up -d
```
Backend em: http://localhost:8080

### Local (fora do Docker)
Pré-requisitos: Java 17+, Maven 3.6+, Python 3.11+

**Passo 1: Preparar o ambiente Python**
```bash
# Terminal 1: subir Python API (Enhanced)
cd data_science

# Criar/ativar ambiente virtual (se não existir)
python3 -m venv sentiment-env
source sentiment-env/bin/activate

# Instalar dependências
pip install -r requirements.txt

# Iniciar API na porta 8000
python enhanced_sentiment_api.py 8000
```

Você deve ver:
```
======================================================================
🚀 SENTIMENT ANALYSIS API - ENDPOINTS ATIVOS
======================================================================
...
Uvicorn running on http://0.0.0.0:8000 (Press CTRL+C to quit)
```

**Passo 2: Rodar o Backend Java**
```bash
# Terminal 2: voltar para backend e rodar
cd backend
mvn spring-boot:run
```

Backend iniciará em: **http://localhost:8080**

**Passo 3: Testar**
```bash
curl -X POST "http://localhost:8080/api/sentiment/predict" \
   -H "Content-Type: application/json" \
   -d '{"text": "Este produto é excelente!"}'
```

**⚠️ Observações:**
- A Python API deve estar rodando ANTES de iniciar o Backend
- O Backend procura a API em `http://localhost:8000`
- Dados não serão persistidos (sem PostgreSQL local)
- Cache Redis não funcionará (sem Redis local)

## 📋 Endpoints

### Informação
```bash
curl http://localhost:8080/
curl http://localhost:8080/api
```

### Análise simples
```bash
curl -X POST "http://localhost:8080/api/sentiment/predict" \
     -H "Content-Type: application/json" \
     -d '{"text": "Produto incrível!"}'
```

### Análise enhanced
```bash
curl -X POST "http://localhost:8080/api/sentiment/predict/enhanced" \
     -H "Content-Type: application/json" \
     -d '{
       "text": "Produto excelente!",
       "rating": 5,
       "recommend_to_friend": true
     }'
```

### Seleção automática de modelo

Escolhe o melhor modelo disponível:

```bash
# Com todos os parâmetros → Modelo enhanced
curl -X POST "http://localhost:8080/api/sentiment/predict/auto?text=Produto incrível&rating=5&recommend_to_friend=true"

# Apenas texto → Modelo original
curl -X POST "http://localhost:8080/api/sentiment/predict/auto?text=Produto incrível"
```

## 🏗️ Arquitetura

O backend Java funciona como um gateway API que:
- Recebe requisições HTTP
- Valida dados de entrada
- Chama o serviço Python internamente
- Retorna respostas formatadas

**Comunicação:**
- Backend Java (8080) → API Python (8000)
- Usa RestTemplate para chamadas HTTP
- Suporta modelos Original e Enhanced

**Roteamento inteligente:**
- `/predict` → Modelo original (texto)
- `/predict/enhanced` → Modelo enhanced (texto + rating + recomendação)
- `/predict/auto` → Escolhe automaticamente baseado nos parâmetros

## 📝 Componentes principais

- `SentimentController.java` - Endpoints REST
- `SentimentService.java` - Lógica de negócio
- `SentimentRequest.java` - DTO de entrada
- `SentimentResponse.java` - DTO de saída
- `SentimentApiApplication.java` - Classe principal

## � Build

```bash
cd backend
mvn clean package
```

## 🐛 Troubleshooting

### Problemas comuns

1. **API Python não responde**
   ```bash
   curl http://localhost:8000/health
   ```
   Verifique se a API está rodando ANTES do Backend.

2. **Porta 8080 já está em uso**
   ```bash
   lsof -i :8080
   ```

3. **Erro no build Maven**
   ```bash
   mvn clean compile
   mvn dependency:resolve
   ```

4. **Ver logs**
   ```bash
   sudo docker-compose logs backend
   ```