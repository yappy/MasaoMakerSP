#!/bin/sh -eux

SELF_DIR=$(dirname "$(realpath "$0")")
cd "${SELF_DIR}"

mkdir -p ulaw pcm
cp ../../original/mc3/omake/*.au ulaw/
for file in ulaw/*.au; do sox "$file" -e signed-integer "pcm/$(basename "$file")"; done
