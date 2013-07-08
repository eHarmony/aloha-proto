# Protocol Buffer Definitions for Aloha Libraries #

This module provides [protocol buffer](http://code.google.com/p/protobuf/) definitions for entities used in the Aloha libraries.  Currently, this only includes

*  _com.eharmony.matching.aloha.score.Scores_: Used for serializing scores output by models.  This allows for trees of polymorphic scores.

Assuming that a downstream project is using [Maven](http://maven.apache.org/) (either 2.x or 3.x), it can use the
Protocol Buffers defined in this project by

```xml

<plugins>
  <!-- ... -->

  <!--
      0.1.10 on Maven 2.X
      0.3.1  on Maven 3.X
  -->
  <plugin>
    <groupId>com.google.protobuf.tools</groupId>
    <artifactId>maven-protoc-plugin</artifactId>
    <version>0.1.10</version>
    <executions>
      <execution>
        <phase>generate-sources</phase>
        <goals>
          <goal>compile</goal>
          <goal>testCompile</goal>
        </goals>
      </execution>
    </executions>
  </plugin>
</plugins>
```
