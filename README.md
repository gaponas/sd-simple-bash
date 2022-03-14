# sd-simple-bash

Software design project

## Сборка проекта

```
$ ./gradlew build
```

## Структура проекта

```
\
|---- src/ru/sd <- исходники
             | ------ main <- метод Main
             | ------ parser <- Парсер команд, берет строку и строит AST
             | ------ interpretator <- Выполняет AST и выводит результат
             | ------ commands <- Содержит встроенные команды, а также (TODO) должен вызывать внешние
|---- src/ru/sd <- тесты (TODO)
```

## licence

no licence
