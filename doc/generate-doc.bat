@echo off
echo Copying last version of test classes
copy /Y ..\..\Android.Infographix.Test\src\com\crocoware\infographix\test\*.java .\src
echo Generating dexy documentation
dexy

REM (this does not work : see https://github.com/dexy/dexy/issues/145)
echo ERROR=%ERRORLEVEL%
IF ERRORLEVEL 1 goto end

echo Copying root README.md file
copy output\ROOT-PROJECT-README.md ..\README.md
:end