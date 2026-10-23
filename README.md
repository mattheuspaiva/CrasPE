# CRAS Pernambuco (CrasPE)

## Sobre o projeto

O CrasPE é um sistema desenvolvido em Java para gerenciar informações dos Centros de Referência de Assistência Social (CRAS) do estado de Pernambuco.

O projeto utiliza Hibernate para persistência de dados e SQL Server como banco de dados. Também permite importar os dados originais de um arquivo CSV.

## Tecnologias utilizadas

- Java
- Maven
- Hibernate ORM
- SQL Server
- CSV

## Estrutura do projeto

```text
CrasPE/
├── dados/
│   └── cras_pernambuco.csv
├── src/
│   └── main/
│       ├── java/
│       │   └── br/com/craspe/
│       │       ├── model/
│       │       ├── dao/
│       │       ├── service/
│       │       ├── view/
│       │       ├── util/
│       │       └── Main.java
│       └── resources/
│           └── hibernate.cfg.xml
├── pom.xml
└── README.md
```



