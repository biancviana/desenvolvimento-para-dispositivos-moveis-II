# Finance App

Aplicativo Android para controle de transações financeiras, permitindo cadastro, edição e exclusão de receitas e despesas.

---

## Requisitos da Atividade Implementados

### 1. Firebase Authentication & Controle de Sessão
* **Cadastro e Login:** Fluxo completo de criação de conta e validação de acesso com e-mail e senha.
* **Logout Seguro:** Botão de saída integrado na `TopAppBar` que encerra a sessão ativa no Firebase de forma síncrona.
* **Controle de Estado de Sessão:** Verificação inicial no ciclo de vida da aplicação para direcionar o usuário automaticamente para a Home caso já esteja autenticado.

### 2. Cloud Firestore (Persistência Remota & CRUD)
* **Operações CRUD Completas:** Inclusão, listagem, alteração e exclusão de transações financeiras (Entradas e Saídas).
* **Relacionamento entre Coleções (1:N):** Cada transação criada é vinculada obrigatoriamente ao `userId` do usuário autenticado.
* **Consultas em Tempo Real:** Listagem dinâmica utilizando `SnapshotListener` convertido em `Flow`, atualizando a interface instantaneamente a cada modificação no banco.

### 3. Segurança do Firestore
* **Regras de Leitura e Escrita:** Configuração estrita no console do Firebase garantindo que um usuário autenticado só consiga ler ou modificar documentos cujo campo `userId` seja igual ao seu próprio `UID`.
* **Tratamento de Permissões:** Fechamento seguro de fluxos assíncronos (`callbackFlow`) ao interceptar exceções de `PERMISSION_DENIED` durante o logout.

### 4. Componentes de UI (Material 3 & Jetpack Compose)
* **Listagens Eficientes:** Uso de `LazyColumn` para renderizar o histórico de transações.
* **Interações e Modais:** Implementação de `AlertDialog` para confirmação de exclusões e `ModalBottomSheet` para formulários.
* **Feedback ao Usuário:** Exibição de `Snackbar` com ação de "Desfazer" (Undo) ao deletar um registro, além de `Loading states` durante a comunicação com o backend.

### 5. Persistência de Preferências com DataStore
* **Jetpack DataStore (Preferences):** Armazenamento local e assíncrono das configurações globais do aplicativo (como a escolha da fonte de dados preferida), gerenciado via fluxos (`Flow`).

---

## Arquitetura e Tecnologias Utilizadas

O projeto adota os princípios de **Clean Architecture** e o padrão de projeto **MVVM (Model-View-ViewModel)**, dividido em camadas lógicas:
* **UI Layer:** Componentes customizados em Jetpack Compose, gerenciamento de estado imutável com `StateFlow` e ciclo de vida integrado.
* **Domain Layer:** Contém as regras de negócio puras (Modelos de domínio e *Use Cases* independentes de frameworks).
* **Data Layer:** Implementação dos repositórios, mapeamento de DTOs (*Data Transfer Objects*) do Firebase e persistência de dados.

**Tecnologias:**
* **Kotlin** (Linguagem oficial)
* **Jetpack Compose** & **Material 3** (Interface declarativa)
* **Koin** (Injeção de Dependência estruturada por módulos)
* **Kotlin Coroutines & Flow** (Programação assíncrona e reativa)
* **Firebase (Auth & Firestore)** (Backend as a Service)

## Telas

### 1. Estado Inicial (Sem Usuários)
![provando que não existe usuário no firebase](https://github.com/user-attachments/assets/c228787b-397d-4a65-9525-7da8547a21a5)
*Comprova que o console do Firebase Authentication não possuía nenhum usuário cadastrado antes do início do teste.*

---

### 2. Criação de Conta no Emulador
![criando nova conta](https://github.com/user-attachments/assets/173e964c-0e4c-45c7-9b12-5bae0befbfeb)
*Interface do formulário de cadastro preenchido e executado dentro do aplicativo.*

---

### 3. Usuário Cadastrado com Sucesso
![provando que após o cadastro, a conta foi criada](https://github.com/user-attachments/assets/01fc8a98-f12e-4891-b9c5-f6c241ca1d6b)
*Console do Firebase atualizado em tempo real, confirmando a persistência do e-mail e geração do UID único.*

---

### 4. Validação de Credenciais Inválidas
![tentando logar sem ter cadastro](https://github.com/user-attachments/assets/b66c1d6f-fde1-4007-bf39-1084ef3ffd5a)
*Tratamento de erro obrigatório exibindo feedback visual na interface ao tentar autenticar com dados incorretos.*

---

### 5. Tela Principal (Área Logada)
![usuário autenticado](https://github.com/user-attachments/assets/25aaf081-a81b-4dda-ada3-eda16ff91267)
*Acesso concedido à dashboard principal (Home) do Finance App após a validação do controle de sessão.*
