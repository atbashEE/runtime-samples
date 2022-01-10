
# Runtime Samples

Contains some samples and specification testing for the Atbash Runtime.

## directory 'specs'

Testing of the specifications using the Testing framework (based on top of _Testcontainers_ and thus requires a Docker client installed.)

You can run the tests on JDK 11 by executing the following command within the directory.

```
mvn
```

It assumes that you have a JDK 11 active as the test itself is always using JDK 11.

The following command runs the Atbash Runtime within a JDK 17 based Docker container and thus test the specification samples in combination with JDK 17.


```
mvn -Datbash.test.container.jdk=jdk17
```


### Tests

-  Servlet defined through Annotation.
