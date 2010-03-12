To open this project in Eclipse, install the Maven Plugin (M2Eclipse) from http://m2eclipse.codehaus.org/

To create the installation directory, install Maven and execute the following command in this directory:
mvn clean package assembly:directory

You will find an installation directory in target\gaalop-1.0.0-bin.dir

To generate the API documentation, change into the api folder and execute:
mvn javadoc:javadoc
or
mvn javadoc:jar

The results will be produced in the api\target folder.
