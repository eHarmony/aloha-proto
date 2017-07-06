# Protocol Buffer Definitions for Aloha Libraries

[![Build Status](https://travis-ci.org/eHarmony/aloha-proto.svg?branch=master)](https://travis-ci.org/eHarmony/aloha-proto)

## Installing Locally

```bash
mvn clean install
```

## Description

This module provides [protocol buffer](http://code.google.com/p/protobuf/) definitions
for entities used in the Aloha libraries.  Currently, this only includes

*  _com.eharmony.aloha.score.Scores_: Used for serializing scores output by
models.  This allows for trees of polymorphic scores.

`aloha-proto` uses the following plugin to compile the protocol buffers to Java
class files:

```xml
<plugin>
  <groupId>org.xolstice.maven.plugins</groupId>
  <artifactId>protobuf-maven-plugin</artifactId>
  <version>0.5.0</version>
</plugin>
```

The `protoc` binary is expected to be on the path, so if compiling
locally, make sure the appropriate `protoc` version is available when doing:

```bash
which protoc
```

## Installing Protocol Buffers 2.4.1

Aloha is currently required to use PB `2.4.1` and `2.4.1` is rather old at this
point. So, it's slightly difficult to install locally on a dev machine since
**brew** no longer supports that version.  To get around this, you can install
it with brew via the included `protobuf241.rb` brew formula:

```bash
cd aloha-proto
brew install ./protobuf241.rb
```

The formula is keg-only--since it conflicts with more modern versions of
protocol buffers--so you'll have to manually update you path.  This can be
done via:

```bash
# Assuming brew installs files to a subdirectory of /usr/local
PROTOBUF_PATH=$(
  for F in $(find /usr/local -name protobuf241 -type d); do
    find $F -name bin | xargs dirname
  done | head -n1
)

export PATH="$PROTOBUF_PATH/bin:$PATH"
```

## License

Aloha is released under the [MIT License](http://opensource.org/licenses/MIT).
