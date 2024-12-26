#!/bin/sh -eux

mkdir -p ulaw pcm
cp ../original/mc3/omake/*.au ulaw/
for file in ulaw/*.au; do sox "$file" -e signed-integer "pcm/$(basename "$file")"; done
