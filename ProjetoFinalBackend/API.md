
# Documentação da API de Gerenciamento de Basquete

## AuthController

O controlador `AuthController` lida com a autenticação e o registro de usuários.

### Endpoints

- **`/auth/register`** (POST)
  - **Descrição:** Registra um novo usuário.
  - **Corpo da Requisição:** `RegisterRequestDTO`
  - **Retorno:** `ResponseEntity<ResponseDTO>`

- **`/auth/login`** (POST)
  - **Descrição:** Realiza o login de um usuário.
  - **Corpo da Requisição:** `LoginRequestDTO`
  - **Retorno:** `ResponseEntity<ResponseDTO>`

---

## CoachController

O controlador `CoachController` gerencia as operações relacionadas aos treinadores, times e treinos.

### Endpoints - Times

- **`/coach/team`** (POST)
  - **Descrição:** Cria um novo time.
  - **Corpo da Requisição:** `TeamDTO`
  - **Retorno:** `ResponseEntity<TeamDTO>`

- **`/coach/team/{id}`** (DELETE)
  - **Descrição:** Exclui um time existente.
  - **Parâmetros:** `id` (Long)
  - **Retorno:** `ResponseEntity<Void>`

- **`/coach/team/{id}`** (PUT)
  - **Descrição:** Atualiza as informações de um time existente.
  - **Parâmetros:** `id` (Long)
  - **Corpo da Requisição:** `TeamDTO`
  - **Retorno:** `ResponseEntity<TeamDTO>`

### Endpoints - Treinos

- **`/coach/training`** (POST)
  - **Descrição:** Cria um novo treino.
  - **Corpo da Requisição:** `TrainingDTO`
  - **Retorno:** `ResponseEntity<TrainingDTO>`

- **`/coach/training/{id}`** (PUT)
  - **Descrição:** Atualiza as informações de um treino existente.
  - **Parâmetros:** `id` (Long)
  - **Corpo da Requisição:** `TrainingDTO`
  - **Retorno:** `ResponseEntity<TrainingDTO>`

- **`/coach/training/{id}`** (DELETE)
  - **Descrição:** Exclui um treino existente.
  - **Parâmetros:** `id` (Long)
  - **Retorno:** `ResponseEntity<Void>`

- **`/coach/training/{trainingId}/player/{playerId}`** (POST)
  - **Descrição:** Adiciona um jogador a um treino.
  - **Parâmetros:** `trainingId` (Long), `playerId` (Long)
  - **Retorno:** `ResponseEntity<Set<PlayerDTO>>`

- **`/coach/training/{trainingId}/player/{playerId}`** (DELETE)
  - **Descrição:** Remove um jogador de um treino.
  - **Parâmetros:** `trainingId` (Long), `playerId` (Long)
  - **Retorno:** `ResponseEntity<Void>`

### Endpoints - Treinadores

- **`/coach`** (GET)
  - **Descrição:** Retorna a lista de todos os treinadores.
  - **Retorno:** `ResponseEntity<List<CoachDTO>>`

- **`/coach`** (POST)
  - **Descrição:** Cria um novo treinador.
  - **Corpo da Requisição:** `CoachDTO`
  - **Retorno:** `ResponseEntity<CoachDTO>`

- **`/coach/{id}`** (GET)
  - **Descrição:** Retorna os detalhes de um treinador específico.
  - **Parâmetros:** `id` (Long)
  - **Retorno:** `ResponseEntity<CoachDTO>`

- **`/coach/{id}`** (DELETE)
  - **Descrição:** Exclui um treinador específico.
  - **Parâmetros:** `id` (Long)
  - **Retorno:** `ResponseEntity<Void>`

- **`/coach/{id}`** (PUT)
  - **Descrição:** Atualiza as informações de um treinador específico.
  - **Parâmetros:** `id` (Long)
  - **Corpo da Requisição:** `CoachDTO`
  - **Retorno:** `ResponseEntity<CoachDTO>`

