# Finance App

Aplicativo Android para controle de transações financeiras, permitindo cadastro, edição e exclusão de receitas e despesas.

O projeto foi desenvolvido com foco em arquitetura modular e demonstra integração com múltiplas fontes de dados.

---

## Funcionalidades

- Adicionar transações (receitas e despesas)
- Editar transações
- Remover transações
- Resumo financeiro em tempo real
- Troca dinâmica da fonte de dados
- Persistência local e remota

---

### Data Layer

Responsável por acessar diferentes fontes de dados:

- Room (Banco local SQLite)
- API REST (PHP + MySQL)
- Firebase Firestore

---

## Troca de fonte de dados

O app implementa um **Repository Switcher**, permitindo alternar a origem dos dados em tempo de execução.

A escolha é persistida via `DataStore Preferences`.

Fontes disponíveis:

- Room (Local)
- API REST (Backend PHP)
- Firebase Firestore

---

## Tecnologias utilizadas

- Kotlin
- Jetpack Compose
- MVVM
- Koin (Dependency Injection)
- Room
- Retrofit
- Firebase Firestore
- DataStore Preferences
- PHP + MySQL (backend)

---

## Backend (API REST)

O backend foi desenvolvido em PHP puro com MySQL.

### Endpoints:

- `GET /transactions.php` → listar transações
- `POST /insert_transaction.php` → inserir transação
- `PUT /update_transaction.php` → atualizar transação
- `DELETE /delete_transaction.php` → remover transação

---

## Fluxo

Usuário
↓
UI (Compose)
↓
ViewModel
↓
UseCase
↓
Repository Switcher
↓
Room | API | Firebase

---

## Banco de dados

Tabela `transactions`:

```sql
CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255),
    amount DOUBLE,
    date DATETIME,
    type VARCHAR(20)
);
```
