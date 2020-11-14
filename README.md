# Mixmicro

Mixmicro is a modern,JVM-based,full stack Java framework designed for support cloud native.

Mixmicro aims to provides all the tools necessary to build JVM applications including:
* Dependency Injection and Inversion of Control
* Aspect Oriented Programming
* Sensible Defaults and Auto-Configuration
* HTTP server
* XDS protocol that integrate Cloud Native better

With Mixmicro you can build Message-Driven Application, HTTP Servers and more whilst for Microservices in particular Mixmicro
 also providers:
* Distributed Transaction
* Distributed Configuration
* Distributed Scheduler
* Service Discovery
* HTTP Client

## Example Application

Example Mixmicro applications can be found in the [Mixmicro example repository]()

## Building From Source

To build from source checkout the code and run:


First: git clone source from github:
```
https://github.com/mixmicro/mixmicro-core.git
```

Second: build
```
mvn clean install
```
if you want to build with regular `mvn` command, you will need Maven v3.5.0 or above.