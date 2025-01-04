#!/bin/sh -ue

SELF_DIR=$(dirname "$(realpath "$0")")
find "${SELF_DIR}" -name "*.sh" -print0 | xargs -0 -I{} sh -c "echo shellcheck {}; shellcheck {}"
