# Dados de Exemplo para Firebase

Este arquivo contém exemplos de dados para configurar o Firebase Firestore com quizzes e questões de exemplo.

## 1. Coleção: `quizzes`

### Quiz 1: História do Brasil
```json
{
  "id": "quiz_001",
  "title": "História do Brasil",
  "description": "Teste seus conhecimentos sobre a história do Brasil, desde o descobrimento até a independência.",
  "category": "História",
  "difficulty": "Intermediário",
  "timeLimit": 30,
  "questionCount": 20,
  "totalPoints": 100,
  "isActive": true,
  "lastUpdated": 1640995200000
}
```

### Quiz 2: Matemática Básica
```json
{
  "id": "quiz_002",
  "title": "Matemática Básica",
  "description": "Operações matemáticas fundamentais: adição, subtração, multiplicação e divisão.",
  "category": "Matemática",
  "difficulty": "Básico",
  "timeLimit": 20,
  "questionCount": 15,
  "totalPoints": 75,
  "isActive": true,
  "lastUpdated": 1640995200000
}
```

### Quiz 3: Ciências Naturais
```json
{
  "id": "quiz_003",
  "title": "Ciências Naturais",
  "description": "Conhecimentos sobre biologia, química e física básica.",
  "category": "Ciências",
  "difficulty": "Intermediário",
  "timeLimit": 25,
  "questionCount": 18,
  "totalPoints": 90,
  "isActive": true,
  "lastUpdated": 1640995200000
}
```

### Quiz 4: Geografia Mundial
```json
{
  "id": "quiz_004",
  "title": "Geografia Mundial",
  "description": "Países, capitais, continentes e características geográficas do mundo.",
  "category": "Geografia",
  "difficulty": "Avançado",
  "timeLimit": 35,
  "questionCount": 25,
  "totalPoints": 125,
  "isActive": true,
  "lastUpdated": 1640995200000
}
```

### Quiz 5: Literatura Brasileira
```json
{
  "id": "quiz_005",
  "title": "Literatura Brasileira",
  "description": "Autores, obras e movimentos literários brasileiros.",
  "category": "Literatura",
  "difficulty": "Avançado",
  "timeLimit": 30,
  "questionCount": 22,
  "totalPoints": 110,
  "isActive": true,
  "lastUpdated": 1640995200000
}
```

## 2. Subcoleção: `quizzes/{quizId}/questions`

### Questões para Quiz 1 (História do Brasil)

#### Questão 1
```json
{
  "id": "q001_001",
  "quizId": "quiz_001",
  "questionText": "Em que ano o Brasil foi descoberto oficialmente?",
  "questionType": "multiple_choice",
  "options": ["1492", "1500", "1501", "1502"],
  "correctAnswer": "1500",
  "explanation": "O Brasil foi descoberto oficialmente em 22 de abril de 1500 por Pedro Álvares Cabral.",
  "points": 5,
  "order": 1
}
```

#### Questão 2
```json
{
  "id": "q001_002",
  "quizId": "quiz_001",
  "questionText": "Quem foi o primeiro rei de Portugal?",
  "questionType": "multiple_choice",
  "options": ["D. Afonso Henriques", "D. João I", "D. Manuel I", "D. Sebastião"],
  "correctAnswer": "D. Afonso Henriques",
  "explanation": "D. Afonso Henriques foi o primeiro rei de Portugal, coroado em 1139.",
  "points": 5,
  "order": 2
}
```

#### Questão 3
```json
{
  "id": "q001_003",
  "quizId": "quiz_001",
  "questionText": "O Brasil foi colônia de Portugal por mais de 300 anos.",
  "questionType": "true_false",
  "options": ["Verdadeiro", "Falso"],
  "correctAnswer": "Verdadeiro",
  "explanation": "O Brasil foi colônia de Portugal de 1500 até 1822, quando proclamou a independência.",
  "points": 5,
  "order": 3
}
```

### Questões para Quiz 2 (Matemática Básica)

#### Questão 1
```json
{
  "id": "q002_001",
  "quizId": "quiz_002",
  "questionText": "Quanto é 15 + 27?",
  "questionType": "multiple_choice",
  "options": ["40", "41", "42", "43"],
  "correctAnswer": "42",
  "explanation": "15 + 27 = 42",
  "points": 5,
  "order": 1
}
```

#### Questão 2
```json
{
  "id": "q002_002",
  "quizId": "quiz_002",
  "questionText": "Quanto é 8 x 7?",
  "questionType": "multiple_choice",
  "options": ["54", "56", "58", "60"],
  "correctAnswer": "56",
  "explanation": "8 x 7 = 56",
  "points": 5,
  "order": 2
}
```

#### Questão 3
```json
{
  "id": "q002_003",
  "quizId": "quiz_002",
  "questionText": "Quanto é 100 ÷ 4?",
  "questionType": "multiple_choice",
  "options": ["20", "25", "30", "35"],
  "correctAnswer": "25",
  "explanation": "100 ÷ 4 = 25",
  "points": 5,
  "order": 3
}
```

### Questões para Quiz 3 (Ciências Naturais)

#### Questão 1
```json
{
  "id": "q003_001",
  "quizId": "quiz_003",
  "questionText": "Qual é o elemento químico mais abundante no universo?",
  "questionType": "multiple_choice",
  "options": ["Hélio", "Hidrogênio", "Oxigênio", "Carbono"],
  "correctAnswer": "Hidrogênio",
  "explanation": "O hidrogênio é o elemento mais abundante no universo, representando cerca de 75% da massa visível.",
  "points": 5,
  "order": 1
}
```

#### Questão 2
```json
{
  "id": "q003_002",
  "quizId": "quiz_003",
  "questionText": "A fotossíntese é um processo realizado apenas por plantas.",
  "questionType": "true_false",
  "options": ["Verdadeiro", "Falso"],
  "correctAnswer": "Falso",
  "explanation": "A fotossíntese também é realizada por algas e algumas bactérias.",
  "points": 5,
  "order": 2
}
```

## 3. Como Inserir os Dados

### Opção 1: Console do Firebase
1. Acesse o console do Firebase
2. Vá para Firestore Database
3. Clique em "Iniciar coleção"
4. Digite o ID da coleção (ex: "quizzes")
5. Adicione os documentos com os IDs especificados
6. Para as subcoleções, clique no documento e adicione uma nova coleção

### Opção 2: Script de Migração
Você pode criar um script para inserir automaticamente os dados:

```javascript
// Exemplo de script para inserir dados
const admin = require('firebase-admin');

// Inicializar Firebase Admin
admin.initializeApp({
  projectId: 'seu-projeto-id'
});

const db = admin.firestore();

// Inserir quizzes
async function insertQuizzes() {
  const quizzes = [
    // ... dados dos quizzes
  ];
  
  for (const quiz of quizzes) {
    await db.collection('quizzes').doc(quiz.id).set(quiz);
  }
}

// Inserir questões
async function insertQuestions() {
  const questions = [
    // ... dados das questões
  ];
  
  for (const question of questions) {
    await db.collection('quizzes').doc(question.quizId)
      .collection('questions').doc(question.id).set(question);
  }
}

// Executar inserção
insertQuizzes().then(() => insertQuestions());
```

## 4. Estrutura Recomendada

### Organização das Coleções:
```
firestore/
├── quizzes/
│   ├── quiz_001/
│   │   ├── (dados do quiz)
│   │   └── questions/
│   │       ├── q001_001
│   │       ├── q001_002
│   │       └── ...
│   ├── quiz_002/
│   └── ...
├── users/
│   └── {userId}/
│       ├── (dados do usuário)
│       └── attempts/
│           └── (tentativas de quiz)
└── categories/
    ├── História
    ├── Matemática
    ├── Ciências
    └── ...
```

### Índices Recomendados:
- `quizzes.category` (para filtrar por categoria)
- `quizzes.difficulty` (para filtrar por dificuldade)
- `quizzes.isActive` (para mostrar apenas quizzes ativos)
- `users.totalScore` (para ranking)

## 5. Dados de Teste Adicionais

### Usuário de Teste:
```json
{
  "uid": "test_user_001",
  "email": "teste@exemplo.com",
  "displayName": "Usuário Teste",
  "photoUrl": null,
  "totalQuizzes": 0,
  "totalScore": 0,
  "averageScore": 0.0,
  "lastLogin": 1640995200000
}
```

### Tentativa de Teste:
```json
{
  "id": "attempt_001",
  "userId": "test_user_001",
  "quizId": "quiz_001",
  "startTime": 1640995200000,
  "endTime": 1640997000000,
  "score": 85,
  "maxScore": 100,
  "correctAnswers": 17,
  "totalQuestions": 20,
  "timeSpent": 1800000,
  "isCompleted": true,
  "answers": "{\"q001_001\": \"1500\", \"q001_002\": \"D. Afonso Henriques\", \"q001_003\": \"Verdadeiro\"}"
}
```

## 6. Notas Importantes

1. **IDs**: Use sempre IDs únicos e descritivos
2. **Timestamps**: Use timestamps em milissegundos
3. **Validação**: Configure regras de segurança no Firestore
4. **Backup**: Faça backup regular dos dados
5. **Testes**: Teste sempre em ambiente de desenvolvimento primeiro

## 7. Próximos Passos

Após inserir os dados de exemplo:
1. Teste o app com os dados inseridos
2. Verifique se a sincronização está funcionando
3. Teste o funcionamento offline
4. Valide as funcionalidades de quiz
5. Verifique o histórico e estatísticas
