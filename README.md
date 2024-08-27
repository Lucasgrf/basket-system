# BasketSystem

BasketSystem é um sistema de gerenciamento de times, jogadores e treinos de basquete. Este projeto utiliza uma stack moderna para fornecer uma solução completa e eficiente.

## Tecnologias Utilizadas

- **Spring**: Framework para a construção da aplicação backend.
- **Angular**: Framework para a construção da aplicação frontend.
- **TailwindCSS**: Framework de CSS para estilização.
- **Docker**: Ferramenta para containerização da aplicação.
- **Postman**: Ferramenta para testes de API.
- **PostgreSQL**: Banco de dados relacional.

## Funcionalidades

- **Gerenciamento de Times**: Criação e administração de times de basquete.
- **Gerenciamento de Jogadores**: Cadastro e monitoramento de jogadores.
- **Gerenciamento de Treinos**: Planejamento e acompanhamento de treinos.
- **Sistema de Credenciais**: Controle de acesso aos treinos através de credenciais.

## Pré-requisitos

- Docker instalado na máquina.
- Postman para testes de API.
- Node.js e npm para o frontend.
- JDK 11+ para o backend.

## Como Executar

### Backend

1. Clone o repositório:
    ```bash
    git clone https://github.com/Lucasgrf/basket-system.git
    cd BasketSystem
    ```

2. Construa a imagem Docker:
    ```bash
    docker build -t basketsystem-backend .
    ```

3. Execute o container Docker:
    ```bash
    docker run -p 8080:8080 basketsystem-backend
    ```

### Frontend

1. Navegue até o diretório do frontend:
    ```bash
    cd ../frontend
    ```

2. Instale as dependências:
    ```bash
    npm install
    ```

3. Execute a aplicação:
    ```bash
    npm start
    ```

### Banco de Dados

1. Execute o container Docker para o PostgreSQL:
    ```bash
    docker run --name basketsystem-db -e POSTGRES_PASSWORD=senha -d -p 5432:5432 postgres
    ```

## API

Utilize o Postman para testar as APIs do backend. Importe a coleção de testes disponível no diretório `postman`.

[Documentação da API](https://github.com/Lucasgrf/basket-system/blob/main/ProjetoFinalBackend/API%20docs.md)

## Contribuição

1. Faça um fork do projeto.
2. Crie uma nova branch:
    ```bash
    git checkout -b minha-nova-funcionalidade
    ```
3. Faça suas alterações e commit:
    ```bash
    git commit -m 'Adiciona nova funcionalidade'
    ```
4. Envie para o repositório remoto:
    ```bash
    git push origin minha-nova-funcionalidade
    ```
5. Abra um Pull Request.

## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo LICENSE para mais detalhes.

---

Espero que isso ajude! Se precisar de mais alguma coisa, é só avisar. 😊
