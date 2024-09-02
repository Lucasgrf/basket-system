# Documentação da API de Gerenciamento de Basquete

## CredentialController

O controlador `CredentialController` lida com as operações relacionadas às credenciais dos usuários.

### Endpoints

- **`/credential`** (GET)
  - **Descrição:** Recupera todas as credenciais.
  - **Retorno:** `ResponseEntity<List<CredentialDTO>>`

- **`/credential/{id}`** (GET)
  - **Descrição:** Recupera uma credencial específica pelo ID.
  - **Parâmetros:** `id` (Long)
  - **Retorno:** `ResponseEntity<CredentialDTO>`

- **`/credential/{id}`** (DELETE)
  - **Descrição:** Deleta uma credencial específica pelo ID.
  - **Parâmetros:** `id` (Long)
  - **Retorno:** `ResponseEntity<Void>`

- **`/credential/{id}`** (PUT)
  - **Descrição:** Atualiza uma credencial específica pelo ID.
  - **Parâmetros:** `id` (Long)
  - **Corpo da Requisição:** `CredentialDTO`
  - **Retorno:** `ResponseEntity<CredentialDTO>`

## CoachController

O controlador `CoachController` gerencia as operações relacionadas aos treinadores, times e treinamentos.

### Endpoints

- **`/coach/team`** (POST)
  - **Descrição:** Cria um novo time.
  - **Corpo da Requisição:** `TeamDTO`
  - **Retorno:** `ResponseEntity<TeamDTO>`

- **`/coach/team/{id}`** (DELETE)
  - **Descrição:** Deleta um time específico pelo ID.
  - **Parâmetros:** 
    - `id` (Long): ID do time.
  - **Retorno:** `ResponseEntity<Void>`

- **`/coach/team/{id}`** (PUT)
  - **Descrição:** Atualiza as informações de um time específico pelo ID.
  - **Parâmetros:** 
    - `id` (Long): ID do time.
  - **Corpo da Requisição:** `TeamDTO`
  - **Retorno:** `ResponseEntity<TeamDTO>`

- **`/coach/training`** (POST)
  - **Descrição:** Cria um novo treinamento.
  - **Corpo da Requisição:** `TrainingDTO`
  - **Retorno:** `ResponseEntity<TrainingDTO>`

- **`/coach/training/{id}`** (PUT)
  - **Descrição:** Atualiza as informações de um treinamento específico pelo ID.
  - **Parâmetros:** 
    - `id` (Long): ID do treinamento.
  - **Corpo da Requisição:** `TrainingDTO`
  - **Retorno:** `ResponseEntity<TrainingDTO>`

- **`/coach/training/{id}`** (DELETE)
  - **Descrição:** Deleta um treinamento específico pelo ID.
  - **Parâmetros:** 
    - `id` (Long): ID do treinamento.
  - **Retorno:** `ResponseEntity<Void>`

- **`/coach/training/{trainingId}/player/{playerId}`** (POST)
  - **Descrição:** Adiciona um jogador específico a um treinamento.
  - **Parâmetros:** 
    - `trainingId` (Long): ID do treinamento.
    - `playerId` (Long): ID do jogador.
  - **Retorno:** `ResponseEntity<Set<PlayerDTO>>`

- **`/coach/training/{trainingId}/player/{playerId}`** (DELETE)
  - **Descrição:** Remove um jogador específico de um treinamento.
  - **Parâmetros:** 
    - `trainingId` (Long): ID do treinamento.
    - `playerId` (Long): ID do jogador.
  - **Retorno:** `ResponseEntity<Void>`

- **`/coach`** (GET)
  - **Descrição:** Recupera todos os treinadores.
  - **Retorno:** `ResponseEntity<List<CoachDTO>>`

- **`/coach`** (POST)
  - **Descrição:** Cria um novo treinador.
  - **Corpo da Requisição:** `CoachDTO`
  - **Retorno:** `ResponseEntity<CoachDTO>`

- **`/coach/{id}`** (GET)
  - **Descrição:** Recupera as informações de um treinador específico pelo ID.
  - **Parâmetros:** 
    - `id` (Long): ID do treinador.
  - **Retorno:** `ResponseEntity<CoachDTO>`

- **`/coach/{id}`** (DELETE)
  - **Descrição:** Deleta um treinador específico pelo ID.
  - **Parâmetros:** 
    - `id` (Long): ID do treinador.
  - **Retorno:** `ResponseEntity<Void>`

- **`/coach/{id}`** (PUT)
  - **Descrição:** Atualiza as informações de um treinador específico pelo ID.
  - **Parâmetros:** 
    - `id` (Long): ID do treinador.
  - **Corpo da Requisição:** `CoachDTO`
  - **Retorno:** `ResponseEntity<CoachDTO>`

- **`/coach/user/{id}`** (GET)
  - **Descrição:** Recupera as informações de um treinador específico pelo ID do usuário.
  - **Parâmetros:** 
    - `id` (Long): ID do usuário.
  - **Retorno:** `ResponseEntity<CoachDTO>`

## PlayerController

O controlador `PlayerController` gerencia as operações relacionadas aos jogadores.

### Endpoints

- **`/player/join`** (POST)
  - **Descrição:** Jogador se junta a um time.
  - **Corpo da Requisição:** `PlayerDTO`
  - **Retorno:** `ResponseEntity<PlayerDTO>`

- **`/player/leave`** (DELETE)
  - **Descrição:** Jogador sai de um time.
  - **Corpo da Requisição:** `PlayerDTO`
  - **Retorno:** `ResponseEntity<Void>`

- **`/player/confirm/{trainingId}`** (POST)
  - **Descrição:** Jogador confirma presença em um treinamento específico.
  - **Parâmetros:** `trainingId` (Long)
  - **Corpo da Requisição:** `PlayerDTO`
  - **Retorno:** `ResponseEntity<PlayerDTO>`

- **`/player/absence/{trainingId}`** (POST)
  - **Descrição:** Jogador informa ausência em um treinamento específico.
  - **Parâmetros:** `trainingId` (Long)
  - **Corpo da Requisição:** `PlayerDTO`
  - **Retorno:** `ResponseEntity<PlayerDTO>`

- **`/player/team/{playerId}`** (GET)
  - **Descrição:** Recupera o time de um jogador específico.
  - **Parâmetros:** `playerId` (Long)
  - **Retorno:** `ResponseEntity<TeamDTO>`

- **`/player/credential/{playerId}`** (GET)
  - **Descrição:** Recupera a credencial de um jogador específico.
  - **Parâmetros:** `playerId` (Long)
  - **Retorno:** `ResponseEntity<CredentialDTO>`

- **`/player/trainings/{playerId}`** (GET)
  - **Descrição:** Recupera todos os treinamentos de um jogador específico.
  - **Parâmetros:** `playerId` (Long)
  - **Retorno:** `ResponseEntity<List<TrainingDTO>>`

- **`/player`** (GET)
  - **Descrição:** Recupera todos os jogadores.
  - **Retorno:** `ResponseEntity<List<PlayerDTO>>`

- **`/player/{id}`** (PUT)
  - **Descrição:** Atualiza as informações de um jogador específico.
  - **Parâmetros:** `id` (Long)
  - **Corpo da Requisição:** `PlayerDTO`
  - **Retorno:** `ResponseEntity<PlayerDTO>`

- **`/player/{id}`** (GET)
  - **Descrição:** Recupera as informações de um jogador específico pelo ID.
  - **Parâmetros:** `id` (Long)
  - **Retorno:** `ResponseEntity<PlayerDTO>`

- **`/player`** (POST)
  - **Descrição:** Cria um novo jogador.
  - **Corpo da Requisição:** `PlayerDTO`
  - **Retorno:** `ResponseEntity<PlayerDTO>`

- **`/player/{id}`** (DELETE)
  - **Descrição:** Deleta um jogador específico pelo ID.
  - **Parâmetros:** `id` (Long)
  - **Retorno:** `ResponseEntity<Void>`

- **`/user/{userId}`** (GET)
  - **Descrição:** Recupera as informações de um jogador a partir do seu ID de usuário.
  - **Parâmetros:** `userId` (Long)
  - **Retorno:** `ResponseEntity<PlayerDTO>`

## TeamController

O controlador `TeamController` gerencia as operações relacionadas aos times.

### Endpoints

- **`/team/{teamId}/players`** (GET)
  - **Descrição:** Recupera todos os jogadores de um time específico.
  - **Parâmetros:** `teamId` (Long)
  - **Retorno:** `ResponseEntity<List<PlayerDTO>>`

- **`/team/{id}`** (GET)
  - **Descrição:** Recupera as informações de um time específico pelo ID.
  - **Parâmetros:** `id` (Long)
  - **Retorno:** `ResponseEntity<TeamDTO>`

- **`/team`** (GET)
  - **Descrição:** Recupera todos os times.
  - **Retorno:** `ResponseEntity<List<TeamDTO>>`

- **`/team/{id}`** (DELETE)
  - **Descrição:** Deleta um time específico pelo ID.
  - **Parâmetros:** `id` (Long)
  - **Retorno:** `ResponseEntity<Void>`

- **`/team/add`** (POST)
  - **Descrição:** Adiciona um novo time.
  - **Corpo da Requisição:** `TeamDTO`
  - **Retorno:** `ResponseEntity<TeamDTO>`

- **`/team/{id}`** (PUT)
  - **Descrição:** Atualiza as informações de um time específico pelo ID.
  - **Parâmetros:** `id` (Long)
  - **Corpo da Requisição:** `TeamDTO`
  - **Retorno:** `ResponseEntity<TeamDTO>`

## TrainingController

O controlador `TrainingController` gerencia as operações relacionadas aos treinamentos.

### Endpoints

- **`/training/{id}`** (GET)
  - **Descrição:** Recupera as informações de um treinamento específico pelo ID.
  - **Parâmetros:** `id` (Long)
  - **Retorno:** `ResponseEntity<TrainingDTO>`

- **`/training`** (GET)
  - **Descrição:** Recupera todos os treinamentos.
  - **Retorno:** `ResponseEntity<List<TrainingDTO>>`

- **`/training/delete/{id}`** (DELETE)
  - **Descrição:** Deleta um treinamento específico pelo ID.
  - **Parâmetros:** `id` (Long)
  - **Retorno:** `ResponseEntity<Void>`

- **`/training/add`** (POST)
  - **Descrição:** Adiciona um novo treinamento.
  - **Corpo da Requisição:** `TrainingDTO`
  - **Retorno:** `ResponseEntity<TrainingDTO>`

- **`/training/update/{id}`** (PUT)
  - **Descrição:** Atualiza as informações de um treinamento específico pelo ID.
  - **Parâmetros:** `id` (Long)
  - **Corpo da Requisição:** `TrainingDTO`
  - **Retorno:** `ResponseEntity<TrainingDTO>`

- **`/team/{id}/trainings`** (GET)
  - **Descrição:** Recupera todos os treinos associados a um time específico.
  - **Parâmetros:** `id` (Long)
  - **Retorno:** `ResponseEntity<List<TrainingDTO>>`

## UserController

O controlador `UserController` gerencia as operações relacionadas aos usuários.

### Endpoints

- **`/user/profile/{id}`** (PUT)
  - **Descrição:** Atualiza o perfil de um usuário específico pelo ID.
  - **Parâmetros:** `id` (Long)
  - **Corpo da Requisição:** `UserDTO`
  - **Retorno:** `ResponseEntity<UserDTO>`

- **`/user/profile/{id}`** (DELETE)
  - **Descrição:** Deleta o perfil de um usuário específico pelo ID.
  - **Parâmetros:** `id` (Long)
  - **Retorno:** `ResponseEntity<Void>`

- **`/user/{id}`** (GET)
  - **Descrição:** Recupera as informações de um usuário específico pelo ID.
  - **Parâmetros:** `id` (Long)
  - **Retorno:** `ResponseEntity<UserDTO>`

- **`/user`** (GET)
  - **Descrição:** Recupera todos os usuários.
  - **Retorno:** `ResponseEntity<List<UserDTO>>`

- **`/user/{id}`** (DELETE)
  - **Descrição:** Deleta um usuário específico pelo ID.
  - **Parâmetros:** `id` (Long)
  - **Retorno:** `ResponseEntity<Void>`

- **`/user/add`** (POST)
  - **Descrição:** Adiciona um novo usuário.
  - **Corpo da Requisição:** `UserDTO`
  - **Retorno:** `ResponseEntity<UserDTO>`

- **`/user/{id}`** (PUT)
  - **Descrição:** Atualiza as informações de um usuário específico pelo ID.
  - **Parâmetros:** `id` (Long)
  - **Corpo da Requisição:** `UserDTO`
  - **Retorno:** `ResponseEntity<UserDTO>`
