# API-WORST-MOVIE

Esta é uma API RESTful desenvolvida com Spring Boot. Seu objetivo é identificar o maior e o menor intervalo dentre os quais produtores ganharam prêmios no Golden Raspberry Awards. A API carrega dados de filmes de uma planilha CSV (`movielist.csv`), os aloca em um banco H2 em memória e fornece um endpoint que retorna o maior e o menor intervalo de vitórias.

  

## Funcionalidade

  

-  **Leitura de dados**: Lê o arquivo `movielist.csv` e converte num array de objetos (atributos: `year;title;studios;producers;winner`);

-  **Alimentação de banco de dados**: Popula um banco de dados H2 com os dados extraídos da planilha ao rodar o projeto;

-  **Cálculo de Intervalos**: Identifica os produtores com o menor e o maior intervalo entre vitórias na premiação;

-  **Endpoint REST**: Fornece um endpoint `/api/movies/min-max-winner` para verificar o resultado, retornando um JSON contendo os produtores com menor intervalo e o com maior intervalo entre vitórias.

-  **Logging**: Inclui logs detalhados (SLF4J com Logback) para monitoramento e depuração.

-  **Testes**: Conta com teste de integração com JUnit e MockMvc para validar o endpoint.

  

## Tecnologias Utilizadas

  

-  **Java 21**: Linguagem principal utilizada.

-  **Spring Boot 3.4.5**: Framework para construção da API.

-  **Spring Data JPA**: Para persistência no banco H2.

-  **H2 Database**: Banco de dados em memória.

-  **OpenCSV 5.9**: Para leitura do arquivo CSV.

-  **SLF4J com Logback**: Para realizar os logs do procedimento da funcionalidade.

-  **JUnit 5 e MockMvc**: Realização dos testes de integração.

-  **Maven**: Gerenciador de dependências e build do projeto.

  

## Estrutura do Projeto

  
```bash
api-worst-movie/
├── src/
│ ├── main/
│ │ ├── java/com/worstmovie/
│ │ │ ├── controller/
| | | | └── MovieController.java
│ │ │ ├── dto/
| | | | └── ProducerInterval.java
│ │ │ ├── entity/
| | | | └── Movie.java
│ │ │ ├── repository/
| | | | └── MovieRepository.java
│ │ │ ├── service/
| | | | └── MovieService.java
│ │ │ └── ApiApplication.java
│ │ └── resources/
│ │ ├── data/
│ │ │ └── movielist.csv
│ │ └── application.properties
│ └── test/
│ └── java/com/worstmovie/
| └── controller/
| └── MovieControllerTest.java
├── pom.xml
└── README.md
```
  

## Pré-requisitos

  

-  **Java 21** com JDK instalado;
-  **Maven**;

  

## Configuração e Execução

1.  **Clonar o Repositório**:
	 git clone https://github.com/augustop01/api-worst-movie.git
	code api-worst-movie
	
2.  **Verifique a planilha movielist.csv**
	Certifique-se de que os dados da planilha estejam seguindo o cabeçalhoo: (year;title;studios;producers;winner);

3. **Compile e instale as dependências**
	mvn clean install

4. **Execute o projeto:**
	mvn spring-boot:run

## Utilizando a API

### Endpoint
- **GET /api/movies/min-max-winner**
- **Descrição**: Retorna os produtores com o menor e o maior intervalo entre vitórias.
- **Exemplo de resposta:**
```json
{
    "min": [{
        "producer": "Joel Silver",
        "interval": 1,
        "previousWin": 1990,
        "followingWin": 1991
    }],
    "max": [{
            "producer": "Joel Silver",
            "interval": 1,
            "previousWin": 1990,
            "followingWin": 1991
        }
    }
```

## Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para detalhes.
