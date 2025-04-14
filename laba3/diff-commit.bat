@echo off
setlocal EnableDelayedExpansion

:: Путь к файлу со списком отслеживаемых файлов
set "WATCHLIST=watchlist.txt"
set "SHOULD_COMMIT=false"

:: Получаем все изменения, включая untracked
for /f "delims=" %%f in ('git status --porcelain') do (
    set "line=%%f"
    set "file=!line:~3!"
    for /f "usebackq delims=" %%w in ("%WATCHLIST%") do (
        if "!file!"=="%%w" (
            echo Обнаружено изменение в отслеживаемом файле: !file!
            set "SHOULD_COMMIT=true"
        )
    )
)

:: Если есть изменения — добавить и закоммитить
if "%SHOULD_COMMIT%"=="true" (
    echo Добавляем и коммитим изменённые файлы...
    for /f "usebackq delims=" %%w in ("%WATCHLIST%") do (
        git add "%%w" 2>nul
    )
    git commit -m "Автоматический коммит: изменения в отслеживаемых классах"
) else (
    echo Отслеживаемые классы не изменены. Коммит не требуется.
)

endlocal
pause
