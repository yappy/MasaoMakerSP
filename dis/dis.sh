#!/bin/sh -eux

# Unzip mc_c.zip then disassemble all

unzip -o ../original/mc2/mc_c.zip -d mc2
for file in ./mc2/*.class; do javap -v $file > ${file}.txt; done

unzip -o ../original/mc3/mc_c.zip -d mc3
for file in ./mc3/*.class; do javap -v $file > ${file}.txt; done
