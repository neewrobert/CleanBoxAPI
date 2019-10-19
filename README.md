# CLEAN BOX API 

Api de para obter os valores obtidos atraves do hardware CLEAN BOX

## Tecnologias

Esta api foi cencebida usado spring boot e as implementacoes do JAX-RS

## Banco de dados

 Por se tratar de uma api de tests, foi decidido usar o banco de dados embarcado H2. Ou seja, toda vez que aplicaçao é resetada, os dados sao perdidos.


### Build e excecucap da aplicacao

Para buildar a aplicacao nao é necessario nenhuma configuracao alternativa, basta usar os comandos do maven para buildar e executar a aplicacao.

BUILD (dentro do diretorio raiz da aplicacao)

```
mvn clean install
```

RUN (dentro do diretorio raiz da aplicacao)

```
mvn spring-boot:run
```

## Executando os testes

Para executar os testes automatizados, basta usar o comando

```
mvn test
```

## Padrao de Arquitetura 
 O padrao de arquitetura para as classes dessa aplicacao segue:
 
 	*DAO - Camada de persistencia da aplicacao
 	*Model - Entidades da aplicacao
 	*Resource - Classes responsavel pelas chamadas HTTP
 	
 OBS:. Vale lembrar, que para esta aplicacao, nao foi incluido uma camada de *BUSINESS* onde fica contido as regras de negocio. Foi decido assim, pois as chamadas eram simples e nao visavam regras complexas.
 
 
## URLs e ENDPOINTS
 
 	URL BASE: cleanbox/
 	
**Laboratorio** 
Para se obter a lista dos ultimos 5 dias de medicao

	[GET] cleanbox/aguainfo/buscaporperiodo/		
	
## Built With

* [SpringBoot](https://spring.io/projects/spring-boot) - SPRING
* [Maven](https://maven.apache.org/) - Dependency Management
* [Eclipse](https://eclispsefundation.org) - The Eclipse Fundation


## Versioning

Para versionar, estou usando o padrao [SemVer](http://semver.org/)

## Authors

* **Newton Santos** - *Initial work* - [NeewRobert](https://github.com/neewrobert)
