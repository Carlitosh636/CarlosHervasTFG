# CarlosHervasTFG

This project is an application for teaching recursive thinking via dynamic diagrams.
The application implements a series of problems adapted for these diagrams, where the user creates the recursive algorithm with the help of a diagram.

To execute, it requires:
- Java Development Kit version 17 or higher.
- Maven 3.9.3
- An IDE, IntelliJ is highly recommended.

The application uses Maven to package and manage the dependencies. 
To package into an executable .jar file, you can either:
- If you have maven installed in your machine, execute in the root folder (/tfg) the command below. After that, execute the generated .jar in /target:
```
mvn clean package
```
- If your IDE has a Maven plugin, use the "clean" and "package" lifecycle actions in that order. Then execute the generated .jar in /target

