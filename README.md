# Bank Test Mode Automation

[![Java CI](https://github.com/cybdar/patterns2/actions/workflows/build.yml/badge.svg)](https://github.com/cybdar/patterns2/actions/workflows/build.yml)

Время: ручное 75 мин, авто 120 мин.

```bash
java -jar artifacts/app-ibank.jar -P:profile=test
./gradlew test