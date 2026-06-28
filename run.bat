@echo off
setlocal

set SCRIPT_DIR=%~dp0
set SRC_DIR=%SCRIPT_DIR%src
set BIN_DIR=%SCRIPT_DIR%bin
set LIB_DIR=%SCRIPT_DIR%lib
set FX_DIR=%LIB_DIR%\javafx
set JAVA_HOME=C:\Program Files\Java\jdk-25.0.3

:: Collect all .java source files
if exist "%TEMP%\sources.txt" del "%TEMP%\sources.txt"
for /r "%SRC_DIR%" %%f in (*.java) do echo %%f >> "%TEMP%\sources.txt"

:: Copy non-Java assets to bin (javac doesn't copy them)
xcopy /s /q /y "%SRC_DIR%\main\assets" "%BIN_DIR%\main\assets\" 2>nul
xcopy /s /q /y "%SRC_DIR%\main\gui\resources" "%BIN_DIR%\main\gui\resources\" 2>nul

:: Compile with JavaFX on the module path
"%JAVA_HOME%\bin\javac" ^
  --module-path "%FX_DIR%" ^
  --add-modules javafx.controls,javafx.graphics,javafx.media ^
  -cp "%LIB_DIR%\junit-4.13.2.jar;%LIB_DIR%\hamcrest-core-1.3.jar" ^
  -d "%BIN_DIR%" ^
  @"%TEMP%\sources.txt"

del "%TEMP%\sources.txt"

:: Add JavaFX native DLLs to PATH so Windows can find them
set PATH=%FX_DIR%\bin;%PATH%

:: Run with JavaFX on the module path (pass --console to skip the GUI)
"%JAVA_HOME%\bin\java" ^
  --module-path "%FX_DIR%" ^
  --add-modules javafx.controls,javafx.graphics,javafx.media ^
  --enable-native-access=javafx.graphics,javafx.media ^
  -cp "%BIN_DIR%;%LIB_DIR%\junit-4.13.2.jar;%LIB_DIR%\hamcrest-core-1.3.jar" ^
  main.game.Main %*

endlocal
