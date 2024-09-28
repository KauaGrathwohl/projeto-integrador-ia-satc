# backend-projeto-integrador-ia-satc
Projeto Integrador de IA: Criação de um Sistema Inteligente de Monitoramento Alimentar.

### Rodando o projeto:
**Para rodar o projeto localmente é necessário seguir os seguintes passos:**
- 1º passo - Criar a imagem da api localmente através do comando: <br>
    ```sudo docker image build -t api:develop .```

- 2º passo - Rodar os containers necessários para o funcionamento da aplicação através do comando: <br>
    ```sudo docker compose up```

**Conectando ao banco de dados** <br>
O docker subirá o container do postgreSQL e portanto ficará disponível para conexão através de SGBD nas seguintes credenciais:
```
Host: localhost
Porta: 5436
User: postgres
Password: postgres
```

# Observações:
- É necessário executar o ***2º passo*** no mesmo diretório em que o arquivo compose.yaml está localizado. 
- O banco de dados é inicializado com as tabelas ao rodar o ***2º passo***, portanto não é necessário alterações no banco.
- Se houver falha ao rodar o ***2º passo***, pode ser necessário excluir o volume do docker para que o banco seja inicializado do zero na próxima execução do ***2º passo***
, a exclusão do volume deve ser executada no mesmo diretório em que o arquivo **compose.yaml** está lozalizado através do comando: ```sudo docker compose down -v```