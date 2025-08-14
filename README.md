# Quiz App Android Firebase

Um aplicativo de quiz completo para Android desenvolvido com Jetpack Compose, Firebase e arquitetura limpa.

## Funcionalidades

### ✅ Implementadas
- **Autenticação de Usuários**
  - Login com email/senha
  - Cadastro de novos usuários
  - Suporte para Google Sign-In (estrutura preparada)
  - Perfis de usuário salvos localmente e no Firebase

- **Sistema de Quiz**
  - Lista de quizzes disponíveis
  - Diferentes tipos de questões (múltipla escolha, verdadeiro/falso, texto)
  - Sistema de pontuação
  - Controle de tempo (opcional)
  - Navegação entre questões

- **Armazenamento e Sincronização**
  - Banco de dados local com Room
  - Sincronização com Firebase Firestore
  - Funcionamento offline
  - Cache local de quizzes e questões

- **Histórico e Estatísticas**
  - Histórico completo de tentativas
  - Estatísticas de desempenho
  - Comparação entre sessões
  - Dashboard com métricas

- **Interface do Usuário**
  - Design Material 3
  - Navegação fluida entre telas
  - Interface responsiva e moderna
  - Suporte para temas claro/escuro

### 🔄 Em Desenvolvimento
- Dashboard avançado com gráficos
- Sistema de ranking entre usuários
- Filtros por categoria e dificuldade
- Modo de estudo/revisão

## Arquitetura

O projeto segue os princípios da **Clean Architecture** com:

- **Presentation Layer**: Compose UI + ViewModels
- **Domain Layer**: Use Cases + Repositories (interfaces)
- **Data Layer**: Repositories (implementações) + Data Sources
- **Infrastructure**: Room Database + Firebase

## Tecnologias Utilizadas

- **Android**: Kotlin, Jetpack Compose, Navigation
- **Arquitetura**: MVVM, Clean Architecture, Repository Pattern
- **Banco de Dados**: Room Database (local), Firebase Firestore (nuvem)
- **Autenticação**: Firebase Authentication
- **Injeção de Dependência**: Hilt
- **Coroutines**: Para operações assíncronas
- **Material Design**: Material 3

## Configuração

### 1. Pré-requisitos
- Android Studio Hedgehog ou superior
- Android SDK 26+
- JDK 11+
- Conta no Google Firebase

### 2. Configuração do Firebase

#### 2.1 Criar Projeto Firebase
1. Acesse [console.firebase.google.com](https://console.firebase.google.com)
2. Clique em "Criar projeto"
3. Digite o nome do projeto (ex: "quiz-app-android-firebase")
4. Siga as instruções para criar o projeto

#### 2.2 Adicionar App Android
1. No console do Firebase, clique no ícone Android
2. Digite o package name: `com.alonsorafael.quizapp_android_firebase`
3. Baixe o arquivo `google-services.json`
4. Substitua o arquivo de exemplo em `app/google-services.json`

#### 2.3 Configurar Firestore
1. No console do Firebase, vá para "Firestore Database"
2. Clique em "Criar banco de dados"
3. Escolha "Iniciar no modo de teste"
4. Selecione a localização mais próxima

#### 2.4 Configurar Authentication
1. No console do Firebase, vá para "Authentication"
2. Clique em "Começar"
3. Habilite "Email/Senha"
4. Habilite "Google" (opcional)

### 3. Estrutura do Banco de Dados

#### 3.1 Coleção: `quizzes`
```json
{
  "id": "quiz_001",
  "title": "História do Brasil",
  "description": "Teste seus conhecimentos sobre a história do Brasil",
  "category": "História",
  "difficulty": "Intermediário",
  "timeLimit": 30,
  "questionCount": 20,
  "totalPoints": 100,
  "isActive": true,
  "lastUpdated": 1640995200000
}
```

#### 3.2 Subcoleção: `quizzes/{quizId}/questions`
```json
{
  "id": "question_001",
  "quizId": "quiz_001",
  "questionText": "Em que ano o Brasil foi descoberto?",
  "questionType": "multiple_choice",
  "options": ["1492", "1500", "1501", "1502"],
  "correctAnswer": "1500",
  "explanation": "O Brasil foi descoberto em 22 de abril de 1500 por Pedro Álvares Cabral",
  "points": 5,
  "order": 1
}
```

#### 3.3 Coleção: `users`
```json
{
  "uid": "user_123",
  "email": "usuario@exemplo.com",
  "displayName": "João Silva",
  "photoUrl": "https://...",
  "totalQuizzes": 5,
  "totalScore": 350,
  "averageScore": 70.0,
  "lastLogin": 1640995200000
}
```

#### 3.4 Subcoleção: `users/{userId}/attempts`
```json
{
  "id": "attempt_001",
  "userId": "user_123",
  "quizId": "quiz_001",
  "startTime": 1640995200000,
  "endTime": 1640997000000,
  "score": 85,
  "maxScore": 100,
  "correctAnswers": 17,
  "totalQuestions": 20,
  "timeSpent": 1800000,
  "isCompleted": true,
  "answers": "{\"question_001\": \"1500\", \"question_002\": \"São Paulo\"}"
}
```

### 4. Executar o Projeto

1. Clone o repositório
2. Abra o projeto no Android Studio
3. Sincronize o Gradle
4. Configure o arquivo `google-services.json`
5. Execute o app em um dispositivo ou emulador

## Estrutura do Projeto

```
app/src/main/java/com/alonsorafael/quizapp_android_firebase/
├── data/
│   ├── local/
│   │   ├── dao/           # Data Access Objects
│   │   ├── entity/        # Entidades do Room
│   │   ├── converter/     # Conversores do Room
│   │   └── QuizDatabase.kt
│   ├── repository/        # Implementações dos repositórios
│   └── mapper/           # Mappers entre entidades e modelos
├── domain/
│   ├── model/            # Modelos de domínio
│   ├── repository/       # Interfaces dos repositórios
│   └── usecase/         # Casos de uso
├── presentation/
│   ├── auth/            # Telas de autenticação
│   ├── home/            # Tela principal
│   ├── quiz/            # Tela de execução do quiz
│   ├── results/         # Tela de resultados
│   ├── history/         # Tela de histórico
│   └── navigation/      # Navegação do app
└── di/                  # Módulos Hilt
```

## Funcionalidades Principais

### Autenticação
- Login com email/senha
- Cadastro de novos usuários
- Perfis salvos localmente e sincronizados

### Quiz
- Lista de quizzes disponíveis
- Execução com navegação entre questões
- Sistema de pontuação automático
- Controle de tempo configurável

### Histórico
- Registro completo de tentativas
- Estatísticas de desempenho
- Comparação entre sessões

### Sincronização
- Download automático de quizzes
- Funcionamento offline
- Sincronização quando online

## Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## Suporte

Para suporte ou dúvidas:
- Abra uma issue no GitHub
- Entre em contato: [seu-email@exemplo.com]

## Roadmap

- [ ] Dashboard com gráficos avançados
- [ ] Sistema de ranking global
- [ ] Modo de estudo/revisão
- [ ] Filtros avançados
- [ ] Notificações push
- [ ] Modo multiplayer
- [ ] Exportação de resultados
- [ ] Temas personalizáveis

