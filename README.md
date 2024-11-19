# Book managing application
This is a book management application consisting of two microservices:

* Book Service

Responsible for managing book data. It handles requests for creating, updating, getting and deleting books.
* Library Service

Responsible for storing and displaying information about books in library. This service subscribes to events sent by the Book Service via Kafka and updates its database when new books are added.

## Tech Stack
* Java (Spring Boot, Data JPA, Web)
* Gradle
* Kafka
* Docker
* PostgreSQL, Liquibase
* Mockito, Junit
* MapStruct, Lombok
* Swagger

## Installation and setup
You will need Docker

1. Clone the repository
2. Build and up docker-composes for both services
3. Start the services

PS
Before creating a new book, you need to create an author for it. This method returns you the author's id.


POST http://localhost:8081/api/authors
Content-Type: application/json


{

    "firstName" : "Dag",
    "lastName" : "Dag",
    "penName" : "Lewis"
}

Then you can create a new book


POST http://localhost:8081/api/books
Content-Type: application/json

{

    "isbn": "979-0-306-40615-7",
    "title": "Alice in Wonderland",
    "genre": "Fantasy",
    "description": "Book about a little girl in another world",
    "authorId":
}
