# Protocol Buffer Definitions for Aloha Libraries #

[![Build Status](https://travis-ci.org/eHarmony/aloha-proto.svg?branch=master)](https://travis-ci.org/eHarmony/aloha-proto)

This module provides [protocol buffer](http://code.google.com/p/protobuf/) definitions for entities used in the Aloha libraries.  Currently, this only includes

*  _com.eharmony.aloha.score.Scores_: Used for serializing scores output by models.  This allows for trees of polymorphic scores.

Assuming that a downstream project is using [Maven](http://maven.apache.org/) (either 2.x or 3.x), the project can use
the Protocol Buffers defined in this project by

1. using the com.google.protobuf.tools maven-protoc-plugin
1. add aloha-proto dependency to the downstream project that will use the protocol buffers
1. properly importing the definitions in the proto

To properly compile protocol buffer definitions under Maven, one should use the `maven-protoc-plugin`.  This allows the
project to import PBs defined outside the project.  Since neither version of `maven-protoc-plugin` for Maven 2 or 3 is
in the central repository at the time of this writing, the repository needs to be included in the POM under the
`pluginRepositories` section.

For maven 2.x:

```xml
    <pluginRepository>
      <id>dtrott-protoc-plugin</id>
      <url>http://maven.davidtrott.com/repository</url>
      <layout>default</layout>
        <releases>
          <enabled>true</enabled>
        </releases>
    </pluginRepository>
```

For maven 3.x:

```xml
    <!-- For compilation under Maven 3.x -->
    <pluginRepository>
      <id>protoc-plugin</id>
      <url>http://sergei-ivanov.github.com/maven-protoc-plugin/repo/releases/</url>
    </pluginRepository>
```

Then include the plugin in the build `plugins` section of the POM:

```xml
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
```

To import the Maven dependency, add to the `dependencies` of the project:

```xml
    <dependency>
        <groupId>com.eharmony</groupId>
        <artifactId>aloha-proto</artifactId>
        <version>${aloha.proto.version}</version>   <!-- defined in the properties section of the POM. -->
    </dependency>
```

Then, in the protocol buffer definitions of the downstream project, just import the files regularly:

    package com.eharmony.matching.common.value;

    option java_outer_classname="ScoredPairingProtoBuffs";
    import "com.eharmony.aloha.score.Scores.proto";

    // More imports here ...

    message ScoredPairing {
        // This is com.eharmony.aloha.score.Scores.Score
        optional Score score = 1;

        // More data ...
    }

Because the importing of Protocol Buffer definitions by `maven-protoc-plugin` may result in something akin to
namespace collisions, it is advisable to name .proto files after the canonical class name of the outer parent class.
This way, there is no need to worry about different Protocol Buffers with the same name.  So, for instance, the above
protocol buffer definition would be named:

`com.eharmony.matching.common.value.ScoredPairingProtoBuffs.proto`

