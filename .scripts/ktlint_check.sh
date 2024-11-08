#!/bin/bash
set -e

KTLINT_VERSION="1.4.1"
TARGET=".bin/ktlint-$KTLINT_VERSION/ktlint"

if [ ! -f "$TARGET" ]; then
  mkdir -p "$(dirname "$TARGET")"
  curl -L "https://github.com/pinterest/ktlint/releases/download/$KTLINT_VERSION/ktlint" > "$TARGET"
  chmod a+x "$TARGET"
fi

"$TARGET" --reporter plain '.' '!**/build/**'

