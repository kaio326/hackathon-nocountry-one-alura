# Sentiment Analysis Hackathon Project

## 🚀 Quick Start (Recomendado)

O jeito mais rápido de rodar tudo é usar o script:

```bash
chmod +x start_server.sh
./start_server.sh
```

Este script:
- ✅ Verifica se você está no diretório correto
- ✅ Inicia todos os containers Docker
- ✅ Mostra URLs de acesso
- ✅ Indica como parar os serviços

Depois acesse: **http://localhost:8080**

---

## 🏁 Como rodar tudo com Docker (passo a passo)

1) **Pré-requisitos**
  - Docker e Docker Compose instalados
  - 4GB RAM e 8GB de disco livres

2) **Clonar o repositório**
  ```bash
  git clone https://github.com/kaio326/hackathon-nocountry-one-alura.git
  cd hackathon-nocountry-one-alura
  ```

3) **Subir todos os serviços**
  ```bash
  sudo docker-compose up -d
  ```
  Primeira execução leva alguns minutos para baixar imagens, instalar dependências Python, compilar o Spring Boot e preparar volumes.

4) **Verificar se está rodando**
  ```bash
  sudo docker-compose ps
  ```
  Todos os serviços devem aparecer como "Up".

5) **Testar no frontend**
  - Abra http://localhost:8080
  - Informe um texto e clique em "📊 Analisar Sentimento"

6) **Testes rápidos via curl**
  - Java API (texto):
  ```bash
  curl -X POST "http://localhost:8080/api/sentiment/predict" \
      -H "Content-Type: application/json" \
      -d '{"text": "Ótimo produto!"}'
  ```
  - Enhanced (texto+rating+recomendação) - via Python API:
  ```bash
  curl -X POST http://localhost:8000/predict/enhanced \
      -H "Content-Type: application/json" \
      -d '{"text":"Este produto é excelente!","rating":5,"recommend_to_friend":true}'
  ```

7) **Parar serviços**
  ```bash
  sudo docker-compose down
  ```

8) **Rebuild após mudanças de código**
  ```bash
  sudo docker-compose up --build -d
  ```

## 🎯 O que é este projeto

Sistema completo de análise de sentimentos com:
- **Modelos de IA**: TF-IDF + Regressão Logística e Random Forest para classificação
- **Backend Java**: Spring Boot fornecendo endpoints REST com validação
- **Serviço Python**: FastAPI microserviço fazendo as previsões
- **Banco de Dados**: PostgreSQL para armazenamento
- **Cache**: Redis para otimização de performance
- **Containers**: Docker Compose para fácil deploy

## 🤖 Modelos de IA

| Modelo | Características | Algoritmo | Acurácia | Quando usar |
|--------|-----------------|-----------|----------|-------------|
| **Original** | Texto | TF-IDF + Regressão Logística | ~88% | Análise básica |
| **Enhanced** | Texto + Rating + Recomendação | TF-IDF + Random Forest | ~92% | Análise avançada com mais dados |

O **Modelo Enhanced** tem melhor acurácia porque usa:
- **Análise de Texto**: TF-IDF (1000 features)
- **Nota (Rating)**: 1-5 estrelas (26% de importância)
- **Recomendação**: Sim/Não (30% de importância)
- **Comprimento do Texto**: Informação contextual

**Expected Improvement**: 26% accuracy gain, especially for edge cases.

### Training Enhanced Model

```bash
# Navigate to notebooks directory
cd data_science/notebooks

# Run the enhanced model training
jupyter notebook enhanced_model_training.ipynb
```

This will create improved models in `data_science/models/enhanced/`

## � Security Configuration

### Variáveis de Ambiente

**NUNCA faça commit do arquivo `.env`**. Ele contém credenciais sensíveis.

1. **Copie o arquivo de exemplo:**
   ```bash
   cp .env.example .env
   ```

2. **Edite `.env` com suas credenciais seguras:**
   ```bash
   nano .env  # ou seu editor favorito
   ```

### Deployment em Produção

Para produção, use o arquivo de configuração seguro:

```bash
# Copie arquivo de produção
cp .env.example .env.prod
# Edite com credenciais de produção

# Deploy com arquivo de composição de produção
docker-compose -f docker-compose.prod.yml up -d
```

### Recursos de Segurança

- ✅ **Sem credenciais no código** - Todos os dados sensíveis usam variáveis de ambiente
- ✅ **Autenticação Redis** - Redis exige senha
- ✅ **CORS restrito** - Permite apenas origens específicas
- ✅ **Validação de entrada** - Limites de tamanho e sanitização
- ✅ **Tratamento de erros** - Mensagens genéricas em produção
- ✅ **Limites de recursos** - CPU e memória limitados
- ✅ **Sem containers privilegiados** - Hardening aplicado
- ✅ **Headers de segurança** - HTTP security headers configurados

### Boas práticas de segurança

- 🔐 **Mude senhas padrão** antes de fazer deploy
- 🚫 **Nunca exponha portas do banco** em produção
- 🔒 **Use HTTPS** em ambientes de produção
- 📊 **Monitore logs** para atividades suspeitas
- 🔄 **Atualizações regulares** de imagens Docker e dependências

## 📋 Serviços e Portas

**RESUMO DE PORTAS - TODAS AS ARQUITETURAS:**

| Serviço | Porta | URL | Notas |
|---------|-------|-----|-------|
| **Web Frontend** | 8080 | http://localhost:8080 | Interface interativa |
| **Java Backend** | 8080 | http://localhost:8080/api | API principal |
| **Python ML API** | 8000 | http://localhost:8000 | Análise de sentimentos |
| **Python ML Docs** | 8000 | http://localhost:8000/docs | Swagger interativo |
| **pgAdmin** | 5050 | http://localhost:5050 | Gerenciamento DB |
| **PostgreSQL** | 5432 | localhost:5432 | Banco de dados |
| **Redis** | 6379 | localhost:6379 | Cache em memória |

## 🎨 Como usar

Após rodar `sudo docker-compose up -d`:
1. Abra http://localhost:8080
2. Digite um texto e clique em "📊 Analisar Sentimento"

Detalhes de endpoints e APIs: veja [backend/README.md](backend/README.md) e [data_science/README.md](data_science/README.md).

## 🔧 Gerenciamento Docker

**Ver logs:**
```bash
sudo docker-compose logs -f [serviço]
```

**Reiniciar:**
```bash
sudo docker-compose restart [serviço]
```

**Rebuild após mudanças:**
```bash
sudo docker-compose up --build -d
```

**Parar tudo e limpar:**
```bash
sudo docker-compose down -v
```

## 📁 Estrutura do projeto

```
├── data_science/          # Modelos e código de treinamento
│   ├── models/           # Modelos treinados
│   ├── datasets/         # Dados de treinamento
│   ├── notebooks/        # Jupyter notebooks
│   └── enhanced_sentiment_api.py  # API FastAPI
├── backend/              # Backend Java Spring Boot
│   ├── src/              # Código Java
│   ├── resources/        # Configurações
│   ├── Dockerfile
│   └── pom.xml           # Dependências Maven
├── docker-compose.yml    # Orquestração de containers
├── Dockerfile           # Container Python
└── requirements.txt     # Dependências Python
```

## � Documentação dos módulos

- [Backend Java (Spring Boot)](backend/README.md) - Endpoints e execução local
- [Data Science (Python ML)](data_science/README.md) - Modelos, notebooks, APIs Python

## 🛠️ Development Setup (opcional)

Para rodar componentes individualmente fora do Docker:
- Python: veja [data_science/README.md](data_science/README.md)
- Java: veja [backend/README.md](backend/README.md)

## 📊 Performance

- **Acurácia**: ~88-92% (depende do modelo)
- **Recall (Negativos)**: 96% (otimizado para detectar críticas)
- **Classes**: Positivo, Neutro, Negativo
- **Features**: TF-IDF com 1000 features

## 🤝 Como contribuir

1. Faça um fork do repositório
2. Crie uma branch para sua feature
3. Faça as mudanças e teste com Docker
4. Envie um pull request

## 📄 Licença

Este projeto faz parte do Hackathon 2025 Oracle/Alura/NoCountry.
