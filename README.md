<div style="text-align: center">
  <img src="https://upload.wikimedia.org/wikipedia/commons/d/d4/Fiap-logo-novo.jpg" alt="logo da fiap"/>
</div>

<h1 align="center">Projeto Parquímetro</h1>
<h4 align="center"> 
	🚀 Em construção... 🚧
</h4>

O novo sistema de parquímetro foi projetado para lidar com a demanda crescente de estacionamento na
cidade. Ele oferece funcionalidades tais, como registro de condutores e veículos, controle de tempo estacionado,
opções flexíveis de pagamento e emissão de recibos.

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado as ferramentas a seguir:

* [Git](https://www.git-scm.com/downloads)
* [Docker](https://docs.docker.com/get-docker/)
* [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
* [Maven 3.9.5 ou superior](https://maven.apache.org/download.cgi)

### Como executar o projeto

```
# Clone este repositório no diretório de sua preferência
git clone https://github.com/rafaelteixeirarnnt/parquimetro-backend.git

# Navegue até o diretório do projeto
cd ./parquimetro-backend

# Execute o seguinte comando para compilar o projeto 
mvn clean install

# Execute o seguinte comando para iniciar o servidor
mvn spring-boot:run

# Abra o navegador e acesse http://localhost:8080/swagger-ui/index.html
```

### Tecnologias

As seguintes ferramentas foram usadas na construção do projeto:

- [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/1.5.16.RELEASE/reference/html/using-boot-devtools.html)
- [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb/)
- [Lombok](https://projectlombok.org/)
- [Spring Web](https://docs.spring.io/spring-framework/reference/web.html)
- [Java Mail Sender](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/mail/javamail/JavaMailSender.html)
- [Swagger](https://swagger.io/tools/swagger-ui/)
- [MapStruct](https://mapstruct.org/)

### Autores

Este projeto foi criado por:

* [Elaine]()
* [Luiz Guilherme](https://www.linkedin.com/in/lzguilhermecp/)
* [Rafael Teixeira](https://www.linkedin.com/in/rafael-teixeira-79161ab6/)
