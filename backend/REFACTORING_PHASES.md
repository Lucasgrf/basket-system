# SportHub — Backend Refactoring Phases

Detailed breakdown of each phase for the backend refactoring.

---

## Phase 1 — Rebranding & Package Restructuring (~2h)

### Goal
Rename everything from `ProjetoFinalBackend` / `com.springboot.projetofinalbackend` to `sporthub-api` / `com.sporthub.api`.

### Steps

1. **Update `pom.xml`**
   - `groupId` → `com.sporthub`
   - `artifactId` → `sporthub-api`
   - `name` → `SportHub API`

2. **Rename Java package**
   - Move all files from `com/springboot/projetofinalbackend/` → `com/sporthub/api/`
   - Update every `package` and `import` statement

3. **Reorganize sub-packages**
   ```
   com.sporthub.api
   ├── config/
   ├── controller/
   ├── dto/
   │   ├── request/
   │   └── response/
   ├── exception/
   ├── mapper/
   ├── model/
   │   └── enums/
   ├── repository/
   ├── security/
   └── service/
   ```

4. **Update `application.properties`** — rename issuer, app name references

5. **Update `compose.yaml`** — rename service from `meu-app` to `sporthub-api`

6. **Update `Dockerfile`** — reflect new JAR name

### Verification
- `./mvnw clean compile` passes
- Docker image builds successfully

---

## Phase 2 — Domain Model Generalization (~5h)

### Goal
Replace basketball-specific entities with sport-agnostic ones using JPA Joined Inheritance.

### 2.1 — New Enums

```java
// com.sporthub.api.model.enums.SportType
public enum SportType {
    BASKETBALL, SOCCER, VOLLEYBALL, HANDBALL, SWIMMING, ATHLETICS, OTHER
}

// com.sporthub.api.model.enums.TechnicianRole
public enum TechnicianRole {
    HEAD_COACH, ASSISTANT_COACH, PHYSICAL_TRAINER, PHYSIOTHERAPIST, ANALYST
}

// com.sporthub.api.model.enums.Role (replaces User.Role inner enum)
public enum Role {
    ADMIN, TECHNICIAN, ATHLETE
}

// com.sporthub.api.model.enums.SessionStatus
public enum SessionStatus {
    SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED
}
```

### 2.2 — Entity Transformations

#### `User` (simplified — auth only)
- Remove `@OneToOne` to `Admin` (admin is just a Role)
- Remove `@OneToOne` to `Credential` (merged into profiles)
- Keep `@OneToOne` to `Athlete` and `Technician`
- Fields: `id`, `username`, `password`, `email`, `photoUrl`, `role`

#### `Athlete` (replaces `Player`)
- Base entity with `@Inheritance(strategy = InheritanceType.JOINED)`
- Common fields: `id`, `nickname`, `birthDate`, `user`, `team`
- Remove: `position`, `height`, `weight`, `age` (move to sport profiles)

#### Sport-Specific Athlete Profiles (Joined Inheritance)
```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "sport_type")
public abstract class Athlete { ... }

@Entity
@DiscriminatorValue("BASKETBALL")
public class BasketballAthlete extends Athlete {
    private String position;  // PG, SG, SF, PF, C
    private double height;
    private double weight;
}

@Entity
@DiscriminatorValue("SOCCER")
public class SoccerAthlete extends Athlete {
    private String position;  // GK, CB, CM, ST, etc.
    private String preferredFoot;
    private int shirtNumber;
}

@Entity
@DiscriminatorValue("VOLLEYBALL")
public class VolleyballAthlete extends Athlete {
    private String position;  // Setter, Outside, Middle, Libero
    private double height;
    private double reachHeight;
}
```

#### `Technician` (replaces `Coach`)
- Fields: `id`, `nickname`, `specialization`, `licenseNumber`, `technicianRole`, `sportType`, `user`, `team`

#### `Team` (generalized)
- Add: `sportType` (enum)
- Rename: `coach` → `headTechnician` (FK to `Technician`)
- Rename: `players` → `athletes`
- Keep: `name`, `address`, `gym`, `foundation`, `emailContact`, `phoneContact`

#### `TrainingSession` (replaces `Training`)
- Add: `description`, `duration` (minutes), `status` (enum)
- Rename: `players` → `athletes`, `confirmedPlayers` → `confirmedAthletes`

#### Entities to DELETE
- `Admin` — empty entity, role on `User` is sufficient
- `Credential` — duplicated user data, merge relevant fields into profiles

### 2.3 — Repository Updates
- `PlayerRepository` → `AthleteRepository`
- `CoachRepository` → `TechnicianRepository`
- `TrainingRepository` → `TrainingSessionRepository`
- Delete: `AdminRepository`, `CredentialRepository`

### Verification
- `./mvnw clean compile` passes
- App starts and Hibernate auto-generates correct schema with joined tables

---

## Phase 3 — Backend Architecture Cleanup (~6h)

### 3.1 — Exception Handling

**New files:**
```
exception/
├── GlobalExceptionHandler.java    // @RestControllerAdvice
├── ResourceNotFoundException.java
├── ResourceAlreadyExistsException.java
└── BusinessRuleException.java
```

**ErrorResponse DTO:**
```java
public record ErrorResponse(
    int status,
    String error,
    String message,
    LocalDateTime timestamp
) {}
```

**GlobalExceptionHandler** handles:
- `ResourceNotFoundException` → 404
- `ResourceAlreadyExistsException` → 409
- `BusinessRuleException` → 422
- `MethodArgumentNotValidException` → 400 (validation errors with field details)
- `AccessDeniedException` → 403
- `Exception` → 500 (catch-all)

### 3.2 — DTOs (Request/Response split)

**Requests:**
```
dto/request/
├── LoginRequest.java
├── RegisterRequest.java
├── TeamCreateRequest.java
├── TeamUpdateRequest.java
├── TrainingSessionCreateRequest.java
├── TrainingSessionUpdateRequest.java
├── AthleteCreateRequest.java
├── AthleteUpdateRequest.java
├── TechnicianCreateRequest.java
└── TechnicianUpdateRequest.java
```

**Responses:**
```
dto/response/
├── AuthResponse.java          // id, token, role
├── UserResponse.java
├── TeamResponse.java
├── TrainingSessionResponse.java
├── AthleteResponse.java
├── TechnicianResponse.java
├── ErrorResponse.java
└── PageResponse.java          // generic wrapper for pagination
```

### 3.3 — Mappers (centralized, no more duplicated toDTO)

```
mapper/
├── UserMapper.java
├── TeamMapper.java
├── AthleteMapper.java
├── TechnicianMapper.java
└── TrainingSessionMapper.java
```

Each mapper:
```java
@Component
@RequiredArgsConstructor
public class TeamMapper {
    public TeamResponse toResponse(Team team) { ... }
    public Team toEntity(TeamCreateRequest request) { ... }
    public void updateEntity(Team team, TeamUpdateRequest request) { ... }
}
```

### 3.4 — Service Layer Rules

**Before (broken pattern):**
```java
public ResponseEntity<TeamDTO> create(@RequestBody TeamDTO team) {
    return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(team));
}
```

**After (clean separation):**
```java
// Service — returns domain objects, throws exceptions
public TeamResponse create(TeamCreateRequest request) {
    if (teamRepository.existsByName(request.name())) {
        throw new ResourceAlreadyExistsException("Team", "name", request.name());
    }
    Team team = teamMapper.toEntity(request);
    return teamMapper.toResponse(teamRepository.save(team));
}

// Controller — thin HTTP adapter
@PostMapping
public ResponseEntity<TeamResponse> create(@RequestBody @Valid TeamCreateRequest request) {
    return ResponseEntity.status(CREATED).body(teamService.create(request));
}
```

### 3.5 — Constructor Injection Everywhere

Replace all `@Autowired` field injection with `@RequiredArgsConstructor` + `private final` fields.

### 3.6 — Delete AdminService God Object

- Move admin-specific logic (like `createUser` with role assignment) into `UserService`
- `AdminController` calls domain services directly with `@PreAuthorize("hasRole('ADMIN')")`

### 3.7 — Delete `generateRandomUser()`

- Athletes must be created with a valid `userId`
- Remove hardcoded Portuguese name list

### Verification
- `./mvnw clean compile` passes
- All endpoints respond with proper JSON structure
- Error responses return `ErrorResponse` format consistently

---

## Phase 4 — Security Hardening (~2h)

### 4.1 — SecurityConfig
```java
// Replace
.anyRequest().permitAll()
// With
.anyRequest().authenticated()
```

### 4.2 — TokenService Improvements
- Add `role` and `userId` claims to JWT payload
- Make expiration configurable via `application.yml`:
  ```yaml
  security:
    jwt:
      secret: ${JWT_SECRET}
      expiration-hours: 2
  ```

### 4.3 — SecurityFilter Improvements
- Constructor injection (replace `@Autowired`)
- Return 401 JSON response on invalid token (currently silently continues)
- Use `ROLE_` prefix for Spring Security compatibility

### 4.4 — Enable @PreAuthorize
Uncomment and fix all `@PreAuthorize` annotations:
```java
@PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")  // on coach/technician endpoints
@PreAuthorize("hasRole('ADMIN')")                     // on admin-only endpoints
@PreAuthorize("isAuthenticated()")                    // on general endpoints
```

### 4.5 — Role Enum Alignment
Ensure `Role` enum values match Spring Security expected format with `ROLE_` prefix handling.

### Verification
- Unauthenticated requests to protected endpoints → 401
- Wrong-role requests → 403
- Valid JWT → 200

---

## Phase 5 — Infrastructure (~4h)

### 5.1 — Swagger / OpenAPI

**Dependency:**
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.6.0</version>
</dependency>
```

**Config:** `application.yml`
```yaml
springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
```

**Annotations on controllers:**
```java
@Tag(name = "Teams", description = "Team management endpoints")
@Operation(summary = "Create a team", description = "Creates a new team for the specified sport")
@ApiResponse(responseCode = "201", description = "Team created")
```

### 5.2 — Pagination

Replace `List<T>` returns with `Page<T>`:

```java
// Repository
Page<Athlete> findByTeamId(Long teamId, Pageable pageable);

// Service
public Page<AthleteResponse> getAthletesByTeam(Long teamId, Pageable pageable) { ... }

// Controller
@GetMapping
public ResponseEntity<Page<AthleteResponse>> list(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "20") int size,
    @RequestParam(defaultValue = "id") String sortBy
) { ... }
```

### 5.3 — RabbitMQ

**Dependency:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

**Use cases:**
1. **Training notification** — When a `TrainingSession` is created, publish event → consumer sends notification
2. **Athlete added to team** — Publish event when an athlete joins a team
3. **Training reminder** — Scheduled event for upcoming sessions

**Structure:**
```
messaging/
├── config/
│   └── RabbitMQConfig.java       // Queues, exchanges, bindings
├── producer/
│   └── EventPublisher.java       // Publishes domain events
├── consumer/
│   └── NotificationConsumer.java // Consumes and logs events
└── event/
    ├── TrainingCreatedEvent.java
    ├── AthleteJoinedTeamEvent.java
    └── TrainingReminderEvent.java
```

**Docker:** Add RabbitMQ to `compose.yaml`:
```yaml
rabbitmq:
  image: rabbitmq:3-management
  ports:
    - '5672:5672'
    - '15672:15672'
```

### Verification
- Swagger UI loads at `http://localhost:8080/swagger-ui.html`
- Paginated endpoints return `Page<T>` with `totalElements`, `totalPages`, etc.
- RabbitMQ management UI at `http://localhost:15672`, queues visible, events published on entity creation

---

## Phase 6 — Testing (~3h)

### Strategy
- **Unit tests** for services (mocked repositories)
- **Integration tests** for controllers (MockMvc + real Spring context)

### Unit Tests (JUnit 5 + Mockito)

```
src/test/java/com/sporthub/api/
├── service/
│   ├── AuthServiceTest.java
│   ├── TeamServiceTest.java
│   ├── AthleteServiceTest.java
│   ├── TechnicianServiceTest.java
│   └── TrainingSessionServiceTest.java
├── controller/
│   ├── AuthControllerTest.java
│   └── TeamControllerTest.java
└── security/
    └── TokenServiceTest.java
```

**Example pattern:**
```java
@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock private TeamRepository teamRepository;
    @Mock private TeamMapper teamMapper;
    @InjectMocks private TeamService teamService;

    @Test
    void create_shouldThrow_whenNameAlreadyExists() {
        when(teamRepository.existsByName("Lakers")).thenReturn(true);
        assertThrows(ResourceAlreadyExistsException.class,
            () -> teamService.create(new TeamCreateRequest("Lakers", ...)));
    }

    @Test
    void create_shouldReturnResponse_whenValid() { ... }
}
```

### Test Coverage Targets
- Services: 80%+
- Security (TokenService): 90%+
- Controllers: happy path + error cases

### Verification
- `./mvnw test` — all green
- `./mvnw verify` — coverage report generated

---

## Phase 7 — DevOps & Documentation (~2h)

### 7.1 — Docker
- Multi-stage Dockerfile (build + runtime)
- `compose.yaml` with healthchecks for PostgreSQL and RabbitMQ
- `.env.example` for environment variables

### 7.2 — GitHub Actions CI
```yaml
# .github/workflows/ci.yml
on: [push, pull_request]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with: { java-version: '21', distribution: 'temurin' }
      - run: ./mvnw clean verify
```

### 7.3 — README.md
- Project description in English
- Architecture diagram (Mermaid)
- Tech stack badges
- How to run (Docker + local)
- API documentation link
- Screenshots

---

## Execution Order Summary

```
Phase 1 (Rebranding) → Phase 2 (Domain) → Phase 3 (Architecture) → Phase 4 (Security)
                                                                          ↓
                                                              Phase 5 (Infrastructure)
                                                                          ↓
                                                              Phase 6 (Testing)
                                                                          ↓
                                                              Phase 7 (DevOps)
```

Each phase is independently compilable. We commit after each phase.
