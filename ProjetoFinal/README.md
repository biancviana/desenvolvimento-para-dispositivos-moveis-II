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

Usuári -> UI (Compose) -> ViewModel -> UseCase -> Repository Switcher -> Room | API | Firebase

---



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

---

## Interface

<img width="372" height="636" alt="tela inicial" src="https://github.com/user-attachments/assets/fb2d9c18-a4a5-4769-acdd-b9cfa0bcb29b" />

<img width="382" height="637" alt="seleção de fonte de dados" src="https://github.com/user-attachments/assets/0aeef9d7-2ece-45ce-875a-26b14ea7d791" />

<img width="395" height="677" alt="salario room" src="https://github.com/user-attachments/assets/46ce3edb-ae9f-4681-9259-08fe72e968da" />

<img width="396" height="670" alt="salario api" src="https://github.com/user-attachments/assets/d35111a9-7499-4686-a7ec-d7c7fa65e00b" />

<img width="401" height="682" alt="salario firebase" src="https://github.com/user-attachments/assets/75b3e0bd-51aa-40f8-b6d8-960e4ec48104" />

<img width="1451" height="472" alt="salario firebase console" src="https://github.com/user-attachments/assets/f89718f2-cd3a-4787-a70f-39cad03be35a" />

<img width="772" height="167" alt="salario api dbeaver" src="https://github.com/user-attachments/assets/d55370df-fd62-4764-8f7f-3b78c2b71b09" />
