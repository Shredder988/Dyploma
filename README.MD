# Дипломный проект по профессии «Тестировщик»

## Документы: 
* [План автоматизании тестирования.](./docs/plan.md) 
* [Отчет по итогам тестирования](./docs/report.md)
* [отчет по итогам автоматизации](./docs/summary.md)

## Описание приложения

### Бизнес-часть

 Веб-сервис, который предлагает купить тур по определённой цене двумя способами:

1. Обычная оплата по дебетовой карте.
2. Уникальная технология: выдача кредита по данным банковской карты.

![](./docs/pic/service.png)

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:
* сервису платежей, далее Payment Gate;
* кредитному сервису, далее Credit Gate.

Приложение в собственной СУБД должно сохранять информацию о том, успешно ли был совершён платёж и каким способом. Данные карт при этом сохранять не допускается.

### СУБД

Заявлена поддержка двух СУБД.:

* MySQL;
* PostgreSQL


## Запуск приложения 

* склонировать репозиторий https://github.com/Negrustin/Dyploma_Netology на локальную машину командой `git clone`
* открыть проект в IDEA
* для запуска контейнеров с MySql, PostgreSQL и Node.js запустить docker-compose.yml, использовав команду `docker-compose up`, далее в случае перезапуска приложения(контейнеров)
  или возникших проблем используем `docker-compose up -d --force-recreat`
* Запуск под MySQL 
`java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`
* Запуск под PostgreSQL `java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`
##  Запуск тecтов

* Запуск под MySQL 
`./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"`
* Запуск под PostgreSQL
`./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"`
##  Allure

* Генерация отчета запускается командой `./gradlew allureReport`
* Открыит отчет в браузере `./gradlew allureServe`
## Заыершение
* После завершения тестов останаваливаем контейнеры командой `docker-compose down`
* Останавливаем сервис нажатием `CTRL + C` в терминале за запущеным сервисом.