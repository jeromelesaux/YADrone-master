#!/bin/bash

mvn install:install-file -Dfile=xuggle-xuggler-5.4.jar -DgroupId=xuggle -DartifactId=xuggle-xuggler -Dversion=5.4 -Dpackaging=jar
mvn install:install-file -Dfile=reflections/reflections-0.9.9-RC1.jar -DgroupId=org.reflections -DartifactId=reflections-maven -Dversion=0.9.9-RC1 -Dpackaging=jar
mvn install:install-file -Dfile=reflections/jade-plugin-common-1.3.8.jar -DgroupId=org.jfrog.jade.plugins.common -DartifactId=jade-plugin-common -Dversion=1.3.8 -Dpackaging=jar
mvn install:install-file -Dfile=core.jar -DgroupId=core -DartifactId=core -Dversion=1.0 -Dpackaging=jar

