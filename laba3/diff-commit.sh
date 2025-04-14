#!/bin/bash

# Путь к файлу со списком отслеживаемых файлов
WATCHLIST="watchlist.txt"

# Получаем изменённые файлы (только рабочая копия)
CHANGED_FILES=$(git diff --name-only)

# Флаг, если есть совпадения
SHOULD_COMMIT=false

# Сравниваем
for file in $CHANGED_FILES; do
    while read -r watched; do
        if [[ "$file" == "$watched" ]]; then
            echo "Изменён отслеживаемый файл: $file"
            SHOULD_COMMIT=true
        fi
    done < "$WATCHLIST"
done

# Если изменения были
if [ "$SHOULD_COMMIT" = true ]; then
    echo "Добавляем в коммит..."
    git add $(cat "$WATCHLIST")
    git commit -m "Автоматический коммит: изменения в отслеживаемых классах"
else
    echo "Отслеживаемые классы не изменены. Коммит не требуется."
fi
