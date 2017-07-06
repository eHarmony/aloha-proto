#!/bin/sh
set -e
# check to see if protobuf folder is empty

EXPECTED_SHA256='eac6969b617f397247e805267da2b0db3ff9e5a9163b123503a192fbb5776567'

if [ ! -d "$HOME/protobuf/lib" ]; then
  wget https://github.com/google/protobuf/releases/download/v2.4.1/protobuf-2.4.1.tar.gz

  # Verify file integrity.
  SHA256=$(shasum -a 256 protobuf-2.4.1.tar.gz | cut -f1 -d' ')
  if [[ "$SHA256" != "$EXPECTED_SHA256" ]]; then
    echo -e"SHA256 doesn't match.\n\texpected: $EXPECTED_SHA256\nactual:   $SHA256"
    exit 1
  fi

  tar -xzvf protobuf-2.4.1.tar.gz
  cd protobuf-2.4.1

  # Patch the src/google/protobuf/message.cc file to add additional include.
   (
cat <<EOD
--- c1/protobuf-2.4.1/src/google/protobuf/message.cc	2011-04-30 10:22:04.000000000 -0700
+++ c2/protobuf-2.4.1/src/google/protobuf/message.cc	2017-07-06 10:24:32.000000000 -0700
@@ -32,6 +32,7 @@
 //  Based on original Protocol Buffers design by
 //  Sanjay Ghemawat, Jeff Dean, and others.

+#include <istream>
 #include <stack>
 #include <google/protobuf/stubs/hash.h>
EOD
) > message.cc.patch

patch src/google/protobuf/message.cc ./message.cc.patch

  export CXXFLAGS="-DNDEBUG $CXXFLAGS"
  ./configure --prefix=$HOME/protobuf --disable-debug --disable-dependency-tracking --with-zlib && make && make install

  cd -
else
  echo "Using cached directory."
fi

export PATH=$HOME/protobuf/bin:$PATH
