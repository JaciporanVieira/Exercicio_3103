# 🚀 Persistência de Tema com DataStore e Flow

Aplicativo Android desenvolvido para exercitar **Persistência de Dados Moderna** e **Reatividade** utilizando **Jetpack Compose** e **Jetpack DataStore**, substituindo o uso de SharedPreferences por uma solução baseada em fluxos de dados assíncronos.

## 📑 Sumário

* [📝 Objetivo](#-objetivo-do-projeto)
* [🎯 Conceitos Aplicados](#-conceitos-aplicados)
* [🏗️ Decisão de Arquitetura](#️-decisão-de-arquitetura)
* [💡 Por que essa abordagem?](#-por-que-essa-abordagem)
* [🛠️ Tecnologias](#️-tecnologias-utilizadas)
* [🚦 Condições de Negócio](#-condições-de-negócio)
* [🧠 Fluxo de Estados](#-fluxo-de-estados)
* [📁 Estrutura de Projeto](#-estrutura-de-projeto)
* [👨‍💻 Autor](#-autor)

## 📝 Objetivo do Projeto

O objetivo foi implementar uma tela de configurações onde a escolha do tema (Claro, Escuro ou Sistema) seja **persistente** e **reativa**. A interface deve responder instantaneamente à mudança de estado e manter a preferência do usuário mesmo após o fechamento completo do aplicativo.

## 🎯 Conceitos Aplicados

* **Jetpack DataStore (Preferences):** Armazenamento de pares chave-valor de forma assíncrona e segura.
* **Kotlin Flow & StateFlow:** Gerenciamento de fluxos de dados reativos que emitem atualizações para a UI.
* **MVVM (Model-View-ViewModel):** Separação clara entre a lógica de persistência e a exibição.
* **Recomposição Dinâmica:** Mudança de cores do MaterialTheme e de componentes específicos em tempo real.
* **Lifecycle Awareness:** Coleta de estados respeitando o ciclo de vida da Activity (através de `collectAsStateWithLifecycle`).

## 🏗️ Decisão de Arquitetura

✅ **Opção escolhida: DataStore + Flow**

Diferente do SharedPreferences, que trabalha de forma síncrona e pode causar bloqueios na *Main Thread* (UI), o DataStore utiliza **Coroutines** e **Flow** para garantir que a leitura/escrita seja segura e não comprometa a performance do aplicativo.

## 💡 Por que essa abordagem?

✔ **Segurança de Thread:** Todas as operações de I/O são executadas fora da Main Thread.
✔ **Reatividade Nativa:** O Flow permite que a UI "escute" as mudanças no disco sem necessidade de listeners manuais ou callbacks complexos.
✔ **Consistência de Dados:** Garante que o estado da UI seja sempre o reflexo fiel do que está salvo na persistência, evitando "estados fantasmas".
✔ **Modernização:** Segue as recomendações atuais da Google para desenvolvimento Android moderno e robusto.

## 🛠️ Tecnologias Utilizadas

* **Kotlin**
* **Jetpack Compose** (UI Declarativa)
* **DataStore Preferences** (v1.1.1)
* **Lifecycle Runtime Compose** (v2.8.7)
* **ViewModel Compose** (v2.8.7)
* **Material 3** (Design System)

## 🚦 Condições de Negócio

### 🔄 Persistência
* O tema escolhido é salvo como uma `String` no DataStore.
* Ao iniciar, o app lê o valor salvo. Se for nulo ou a primeira execução, assume o padrão `SYSTEM`.

### 🎨 Reatividade Visual
* **LIGHT:** Força a paleta de cores clara (`lightColorScheme`).
* **DARK:** Força a paleta de cores escura (`darkColorScheme`).
* **SYSTEM:** Segue automaticamente a configuração atual do sistema Android.

### 🔘 Componente Dinâmico
* Implementação de um botão cuja cor de fundo (containerColor) altera-se dinamicamente:
    * **Modo Dark:** Cor Verde Água (`0xFF03DAC5`).
    * **Modo Light/System:** Cor Roxa (`0xFF6200EE`).
* O texto do botão também altera sua cor para garantir o contraste e acessibilidade.

## 🧠 Fluxo de Estados

```text
Seleção na UI (RadioButton)
       │
       ▼
Chamada na ViewModel (changeTheme)
       │
       ▼
Escrita Assíncrona (DataStore Edit)
       │
       ▼
Emissão via Flow (ThemePreferenceManager)
       │
       ▼
Coleta de Estado (StateFlow na ViewModel)
       │
       ▼
Recomposição da UI (MainActivity observa via collectAsState)
       │
       ▼
MaterialTheme e Botão reagem instantaneamente
```
---

## 📁 Estrutura de Projeto

```text
📦 app
┣ 📜 MainActivity.kt (UI e MaterialTheme Wrapper)
┣ 📜 ThemeViewModel.kt (Gerenciamento de Estado e Coroutines)
┣ 📜 ThemePreferenceManager.kt (DataStore Implementation)
┣ 📜 libs.versions.toml (Version Catalog)
┗ 📜 build.gradle.kts (Kotlin DSL)
```
---

## 👨‍💻 Autor

**Jaciporan Vieira**
* 💼 Desenvolvedor FullStack
* ☁️ Experiência com Java, Cloud e Mobile

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=flat&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/jaciporan-vieira-silva-483564158/)
[![Email](https://img.shields.io/badge/Email-D14836?style=flat&logo=gmail&logoColor=white)](mailto:jaciporan@email.com)

