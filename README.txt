To open this project in Eclipse, install the Maven Plugin (M2Eclipse) from http://m2eclipse.codehaus.org/

To create the installation directory, install Maven and execute the following command in this directory:
mvn clean package assembly:directory

Note, that the current version of Java Development Kit (Version 8) leads to a NullPointerException while compiling because of the usage of ANTLR version 3. In future development of Gaalop the usage of version 4 of ANTLR is planned. 
Use JDK Version 7 for compiling the current version of Gaalop.

You will find an installation directory in target\gaalop-1.0.0-bin.dir

To generate the API documentation, change into the api folder and execute:
mvn javadoc:javadoc
or
mvn javadoc:jar

The results will be produced in the api\target folder.
