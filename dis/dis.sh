#!/bin/sh -eux

# Unzip mc_c.zip then disassemble all

unzip -f ../original/files/mc_c.zip
for file in ./*.class; do javap -v $file > ${file}.txt; done
