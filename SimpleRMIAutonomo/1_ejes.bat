
rem -Djava.rmi.server.useCodebaseOnly=false
rem java -Djava.rmi.server.useCodebaseOnly=false -Djava.rmi.server.codebase=file:///%cb% -jar %cb% Server 
rem java -Djava.rmi.server.codebase=file:///%cb% -jar %cb% Server 

echo off
echo inicia el servidor
echo uso:
echo 1_ejes HOSTNAME (en caso de omitirlo se usa localhost)
echo on

set cb=%cd%\tstRMI.jar

if [%1] NEQ [] goto conHost
java -Djava.rmi.server.codebase=file:%cb% -jar %cb% Server 
goto fin

:conHost
if [%2] NEQ [] goto conDelta
java -Djava.rmi.server.codebase=file:%cb% -jar %cb% Server %1
goto fin

:conDelta
java -Djava.rmi.server.codebase=file:%cb% -jar %cb% Server %1 %2
:fin
