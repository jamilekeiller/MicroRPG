@echo off
chcp 65001 > nul
cd /d "%~dp0"

rem Garante que o Java seja encontrado mesmo se o PATH estiver desatualizado
if not defined JAVA_HOME (
    if exist "C:\Program Files\Microsoft\jdk-21.0.12.101-hotspot\bin\javac.exe" (
        set "JAVA_HOME=C:\Program Files\Microsoft\jdk-21.0.12.101-hotspot"
    )
)
if defined JAVA_HOME set "PATH=%JAVA_HOME%\bin;%PATH%"
if exist "%USERPROFILE%\tools\apache-maven-3.9.16\bin\mvn.cmd" (
    set "PATH=%USERPROFILE%\tools\apache-maven-3.9.16\bin;%PATH%"
)
set "MAVEN_OPTS=-Dstdout.encoding=UTF-8"

where mvn > nul 2> nul
if errorlevel 1 goto semMaven

rem Jeito do professor: mvn clean compile (com -Xlint:all -Werror no pom.xml) + mvn exec:java
echo Compilando com Maven (mvn clean compile)...
call mvn -q clean compile
if errorlevel 1 goto erro
echo.
call mvn -q exec:java
goto fim

:semMaven
rem Alternativa sem Maven: compila direto com javac, com a mesma regra rigida
echo Maven nao encontrado. Compilando com javac...
if not exist target\classes mkdir target\classes
javac -Xlint:all -Werror -encoding UTF-8 -d target\classes src\main\java\edu\pe\senac\br\rpg\*.java
if errorlevel 1 goto erro
echo.
java -Dstdout.encoding=UTF-8 -cp target\classes edu.pe.senac.br.rpg.Main
goto fim

:erro
echo.
echo Erro ao compilar. Veja as mensagens acima.

:fim
echo.
pause
