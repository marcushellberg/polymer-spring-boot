# Polymer and Spring Boot full stack example

This is an example application showing you how to build a full stack Polymer application with a Spring Boot backend and JWT based token authentication.
 
## Requirements

- Java 1.8
- Maven
- NPM
- Bower

Before you start, be sure you have Node installed. If you are on Mac, I suggest using [Homebrew](http://brew.sh/) to install it. 
When you have node installed, install bower:
 
 ```npm install -g bower```
 
## Running

You can start the application locally by running:
 
```mvn package spring-boot:run```

Open [http://localhost:5000](http://localhost:5000) in your browser and log in with "user"/"user"

# Production

For a production optimized version, run with the `production` profile. This will run vulcanize on your HTML imports to bundle them all into one file.

```mvn clean package spring-boot:run -Pproduction```

## Resources for Polymer development

- [Polymer Homepage](https://www.polymer-project.org/) - catalog of Polymer elements and guides
- [Vaadin Elements](https://vaadin.com/elements) - open source web components for business oriented apps.

## References 
http://technicalrex.com/2015/02/20/stateless-authentication-with-spring-security-and-jwt/
http://blog.jdriven.com/2014/10/stateless-spring-security-part-2-stateless-authentication/
http://kielczewski.eu/2014/12/spring-boot-security-application/