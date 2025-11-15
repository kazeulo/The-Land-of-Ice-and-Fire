@echo off
echo Compiling project...

rem Get all java files recursively under src\main and save to a temp file
dir /s /b src\main\*.java > sources.txt

rem Compile from the list of files
javac -d bin @sources.txt

if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b
)

echo Running the game...
java -cp bin main.game.Main
pause