# pdf-converter-poc
Proof of concept to convert MS Word (.doc, .docx) and MS Excel (.xls, .xlsx) documents to PDF format. Simple Spring Boot web application using the [Aspose](https://www.aspose.com/) evaluation libraries for conversion functionality. Presentation tier uses [Thymeleaf](http://www.thymeleaf.org/) templates and [Materialize](http://materializecss.com/).

> N.B. A license is needed for commercial use of Aspose. This application is for testing purposes only.

### Usage
Maven build creates 2 artifacts:

 *    pdf-converter-poc.war - a standalone self-executing Spring Boot war archive.
 *    pdf-converter-poc.war.original - a Tomcat deployable war. Remove the .original suffix when copying to the webapps folder. 

To run the standalone version:

`java -jar target/pdf-converter-poc.war`

#### url: http://localhost:8080/pdf-converter-poc

> File upload size is restricted to 5MB
