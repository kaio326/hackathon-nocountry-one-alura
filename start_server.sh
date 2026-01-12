#!/bin/bash

echo "🚀 Iniciando Sentiment Analysis Hackathon Server..."
echo "=================================================="

# Verificar se estamos no diretório correto
if [ ! -f "docker-compose.yml" ]; then
    echo "❌ Erro: docker-compose.yml não encontrado. Execute este script no diretório raiz do projeto."
    exit 1
fi

echo "📦 Construindo e iniciando containers..."
echo "⏳ Isso pode levar alguns minutos na primeira execução..."
echo ""

# Iniciar serviços
sudo docker-compose up -d

echo ""
echo "✅ Containers iniciados!"
echo ""
echo "🔍 Verificando status dos serviços..."
sudo docker-compose ps

echo ""
echo "🌐 URLs de acesso:"
echo "   📱 Interface Web Melhorada: http://localhost:8080"
echo "    API Enhanced: http://localhost:8000/docs"
echo "   🐍 API Python ML: http://localhost:8000/docs"
echo ""
echo "🧪 Para testar: ./test_enhanced_frontend.sh"
echo ""
echo "🛑 Para parar: sudo docker-compose down"