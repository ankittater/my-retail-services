# My Retail Price Services

Provide restful services to fetch and update product details,price

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
 
  Java 8 and Maven or Docker
 
 
### Installing

- Running with maven

```
mvn clean install
mvn test
java -jar target/my-retail-0.1.jar
```

- Using docker
```
docker build -f Dockerfile -t my-retail .
docker run -p 8080:8080 my-retail
```
     
## Running the tests
```
mvn test
```

## Built With

* [Micronaut](https://micronaut.io/index.html) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management


## Authors

* **Ankit Kumar Tater** - [Linkedln](https://www.linkedin.com/in/ankittater/)

