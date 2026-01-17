# Patterns 2 - Bank Test Automation

![Java CI](https://github.com/cybdar/patterns2/actions/workflows/build.yml/badge.svg)

### Запуск тестов

```bash
java -jar artifacts/app-ibank.jar -P:profile=test &
sleep 15
./gradlew test -Dselenide.headless=true
```

### Время тестирования

* Ручное тестирование: 75 минут
* Автоматизация: 120 минут