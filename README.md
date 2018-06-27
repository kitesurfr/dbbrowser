# Project Title

Simple web based database browser for MySQL

## Getting Started

1) Run ddl.sql in your MySQL db server with sufficient privileges. This will will create a database 'dbbrowser', 
user 'dbbrowser' with restricted grants and a single table to store connections.

```
mysql> source ddl.sql
```

2) Run `mvn spring-boot:run`
3) Navigate to http://localhost:8080/dbbrowser/browser.html to launch the web app

### Prerequisites

1) This project is build with Maven so make sure maven is installed
2) Install a MySQL server to host the give 'dbbrowser' database

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

