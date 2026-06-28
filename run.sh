#!/usr/bin/env bash
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

# ---------- Find Java -----------------------------------------------------
if ! command -v javac &>/dev/null; then

    # 1. Ask Windows where javac actually lives (honours the real Windows PATH)
    JAVAC_WIN=$(where.exe javac 2>/dev/null | head -1 | tr -d '\r\n')
    if [ -n "$JAVAC_WIN" ]; then
        export PATH="$(dirname "$(cygpath -u "$JAVAC_WIN")"):$PATH"

    # 2. Use JAVA_HOME if the caller set it (same variable run.bat uses)
    elif [ -n "$JAVA_HOME" ]; then
        export PATH="$(cygpath -u "$JAVA_HOME")/bin:$PATH"

    # 3. Hard-coded fallback matching run.bat's JAVA_HOME
    elif [ -f "/c/Program Files/Java/jdk-25.0.3/bin/javac.exe" ]; then
        export PATH="/c/Program Files/Java/jdk-25.0.3/bin:$PATH"

    else
        echo "ERROR: javac not found." >&2
        echo "  Fix: set JAVA_HOME=C:\\path\\to\\jdk  then re-run." >&2
        exit 1
    fi
fi

# ---------- Paths ---------------------------------------------------------
FX_DIR="lib/javafx"
LIB="lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar"
MODS="javafx.controls,javafx.graphics,javafx.media"

# Let Windows find the JavaFX native DLLs (glass.dll, prism_d3d.dll, …)
export PATH="$FX_DIR/bin:$PATH"

# ---------- Compile -------------------------------------------------------
echo "=== Compiling ==="
find src -name "*.java" > /tmp/sources.txt
javac \
  --module-path "$FX_DIR" \
  --add-modules "$MODS" \
  -cp "$LIB" \
  -d bin \
  @/tmp/sources.txt
rm -f /tmp/sources.txt

# ---------- Resources -----------------------------------------------------
echo "=== Syncing resources ==="
mkdir -p bin/main/gui/resources
cp -r src/main/gui/resources/. bin/main/gui/resources/
[ -d src/main/assets ] && cp -rn src/main/assets/. bin/main/assets/ 2>/dev/null || true

# ---------- Run -----------------------------------------------------------
echo "=== Starting The Land of Ice and Fire ==="
java \
  --module-path "$FX_DIR" \
  --add-modules "$MODS" \
  --enable-native-access=javafx.graphics \
  -cp "bin;$LIB" \
  main.game.Main "$@"
