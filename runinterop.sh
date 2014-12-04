#!/bin/bash

if [ ! -f interop/InteropCPP ]
then
  echo Interop CPP example not found, please check whether it is built.
  exit 1
fi

if [ ! -f interop/InteropJAVA.class ]
then
  echo Interop Java example not found, please check whether it is built.
  exit 1
fi

echo Running GemFire QuickStart Interop example ...

export CLASSPATH="${GEMFIRE}/lib/gemfire.jar:./interop"
export PATH="${PATH}:${GEMFIRE}/bin"

interop/InteropCPP &

java InteropJAVA &

echo Finished Interop example.


