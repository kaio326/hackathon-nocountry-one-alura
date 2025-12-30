# Para iniciar a api de previsao de sentimento:

## Instalar FastAPI e uvicorn
```
pip install fastapi uvicorn
```
## Rodar o servidor, vá ao diretorio data_science e corra o comando
```
python sentiment_api.py
```

# Para testar a api

## Health check
```
curl http://localhost:5000/health
```

## Predição única
```
curl -X POST http://localhost:5000/predict \
  -H "Content-Type: application/json" \
  -d '{"text": "Produto excelente!"}'
```

## Predição em lote
```
curl -X POST http://localhost:5000/predict_bulk \
  -H "Content-Type: application/json" \
  -d '{"texts": ["Adorei!", "Péssimo", "Normal"]}'
```

## Documentação interativa
```
http://localhost:5000/docs

```
