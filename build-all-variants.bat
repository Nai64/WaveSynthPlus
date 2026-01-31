@echo off
REM Build all variants of WaveSynth
echo.
echo ========================================
echo Building WaveSynth - All Variants
echo ========================================
echo.

echo [1/3] Building FULL version (all features)...
call gradlew.bat assembleFullRelease
if %errorlevel% neq 0 goto :error

echo.
echo [2/3] Building LITE version (no backgrounds)...
call gradlew.bat assembleLiteRelease
if %errorlevel% neq 0 goto :error

echo.
echo [3/3] Building LEGACY version (older devices)...
call gradlew.bat assembleLegacyRelease
if %errorlevel% neq 0 goto :error

echo.
echo ========================================
echo BUILD SUCCESSFUL - All variants built!
echo ========================================
echo.
echo Output files:
echo   Full:   app\build\outputs\apk\full\release\app-full-release-unsigned.apk
echo   Lite:   app\build\outputs\apk\lite\release\app-lite-release-unsigned.apk
echo   Legacy: app\build\outputs\apk\legacy\release\app-legacy-release-unsigned.apk
echo.
pause
exit /b 0

:error
echo.
echo ========================================
echo BUILD FAILED - Check errors above
echo ========================================
echo.
pause
exit /b 1
