##README GOES HERE AS SPECIFIED IN THE ASSIGNMENT BRIEF

Question 7.

a. After implementing the jdepend maven plugin in the pom.xml we can   
    go to our directory through terminal and run mvn jdepend:generate to
    generate our a jdepend report.
    
b. when we open jdepend-report.xml there is a dependsUpon tag which 
    lists the dependancies our project uses. IntelliJ then has an analyze
    dependancies tool which I could compare with my jdepends report. my
    jdepend report had no cyclic dependencies which means in the project 
    architecture the top layers only depend on the bottom layers but the bottom
    layers don't depend on the top layers
    
c. to generate standalone cli applications in maven we have to go into the
    directory of our mvn project and run it using mvn clean package. This will
    generate the jar file for our main method. If we wish to clear files that 
    running mvn clean package created we can just write mvn clean. Additionally
    mvn test will run test files that mvn detects.
    
 Question 8.
 
  My program reduces possible memory leaks by closing statements and connections
  after using them. Initially i did not close my statements or connections which resulted
  in my program running very slowly due to possible memory leaks. additionally my program
  creates a new instance of student or degree when I call read methods which remain referenced
  until the program stops. If dealing with larger queries or databases this could also
  result in memory leaks, however the queries we used were not complicated enough for this
  to have a major effect.