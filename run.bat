@echo off
title LinkSwift - Acortador de URLs
echo ==============================================================
echo    Iniciando LinkSwift: Acortador de URLs con Spring Boot
echo ==============================================================

set "JAVA_HOME=C:\Users\Master\.p2\pool\plugins\org.eclipse.justj.openjdk.hotspot.jre.full.win32.x86_64_21.0.7.v20250502-0916\jre"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo [1/2] Verificando entorno Java 21...
java -version

echo.
echo [2/2] Levantando aplicacion Spring Boot en http://localhost:8080 ...
echo Puedes abrir tu navegador en: http://localhost:8080
echo Presiona Ctrl+C para detener el servidor.
echo --------------------------------------------------------------

call mvnw.cmd spring-boot:run
pause
