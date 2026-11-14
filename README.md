# 🍰 La Bolaria — API de Gestão, Fichas Técnicas e Engenharia de Cardápio

API RESTful desenvolvida em **Java 25** e **Spring Boot 4** para o gerenciamento de insumos, catálogo de produtos, fichas técnicas de receitas e cálculo automatizado de **CMV (Custo de Mercadoria Vendida)** e margem de lucro de uma confeitaria real.

---

## 🔒 Confidencialidade & Proteção de Segredo Industrial (Produção vs. Portfólio)

Este sistema foi construído e está em operação para uma confeitaria real (**La Bolaria**). Para proteger a propriedade intelectual e as receitas originais da empresa, o projeto adota duas camadas de proteção:

1. **No Repositório GitHub (Código-Fonte):**
   - As fichas técnicas e proporções reais da confeitaria são mantidas exclusivamente em arquivos locais ignorados pelo Git ([.gitignore](.gitignore)).
   - Para fins de demonstração e execução local por recrutadores/avaliadores, o projeto inclui o seeder público [`DemoDataSeeder.java`](src/main/java/com/laBolaria/gestao/config/DemoDataSeeder.java), que popula automaticamente o banco com **receitas e insumos fictícios** caso o banco esteja vazio.
2. **Em Produção (Acesso Híbrido 2 em 1):**
   - Os endpoints de produção (`/api/produtos/**` e `/api/ingredientes/**`) são protegidos pelo filtro [`AccessKeyFilter`](src/main/java/com/laBolaria/gestao/config/AccessKeyFilter.java) via chave criptográfica SHA-256 (`X-Access-Key`).
   - No Front-end, visitantes e recrutadores podem clicar em **"Entrar como Visitante (Modo Demo)"** para testar todo o CRUD, fichas técnicas e recálculo de custos em tempo real com dados fictícios, sem necessidade de senha e sem acesso aos dados confidenciais da confeitaria.

---

## 🚀 Tecnologias Utilizadas

- **Java 25**
- **Spring Boot 4.1** (Spring Web MVC, Spring Data JPA, Bean Validation)
- **PostgreSQL** (Banco de dados relacional)
- **SpringDoc OpenAPI 3 / Swagger UI** (Documentação interativa da API)
- **Docker & Docker Compose** (Containerização multi-stage para deploy em nuvem)
- **Lombok**

---

## ⚙️ Funcionalidades Principais

- **Gestão de Insumos (`/api/ingredientes`):**
  - Controle de ingredientes por `GRAMA`, `MILILITRO` ou `UNIDADE`.
  - Custo unitário base com precisão de 4 casas decimais (`BigDecimal`) e controle de estoque.
  - Travas de integridade referencial (impede exclusão de insumos vinculados a fichas técnicas ativas).
- **Catálogo de Produtos (`/api/produtos`):**
  - Classificação por linhas de produção (`CASEIRO_TRADICIONAL`, `VULCAO`, `RECHEADO_FESTA`, `POTE`).
  - Cálculo dinâmico do custo total da receita (`custoTotalReceita`) a partir da soma dos subtotais da ficha técnica.
- **Engenharia de Ficha Técnica (`/api/produtos/{produtoId}/itens`):**
  - Composição de receitas vinculando insumos e quantidades exatas.
  - Cálculo instantâneo de subtotal por ingrediente e impacto imediato no CMV do produto quando o preço de um insumo é atualizado.

---

## 🛠️ Como Executar Localmente (Modo Demonstração)

1. **Suba o banco PostgreSQL via Docker Compose:**
   ```bash
   cp .env.example .env
   docker compose up -d
   ```

2. **Execute a aplicação Spring Boot:**
   ```bash
   ./mvnw spring-boot:run
   ```
   > Ao iniciar com o banco vazio, o `DemoDataSeeder` carregará automaticamente **12 insumos e 8 produtos fictícios de demonstração** com suas respectivas fichas técnicas.

3. **Acesse a documentação Swagger UI:**
   - `http://localhost:8080/swagger-ui.html`
