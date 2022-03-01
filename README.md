# Дипломный проект по курсу «Тестировщик ПО»
[План дипломного проекта](https://github.com/Nimmo89/Diplomos/blob/main/Plan.md)

[Отчет по итогам тестирования]()

[Отчет по итогам автоматизации]()

# Задача
Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.
Приложение представляет из себя веб-сервис.
<img width="705" alt="service" src="https://user-images.githubusercontent.com/74010731/139225003-2a1df45c-813d-40dd-9f13-900735a7cb02.png">

Приложение предлагает купить тур по определённой цене с помощью двух способов:
1. Обычная оплата по дебетовой карте
1. Уникальная технология: выдача кредита по данным банковской карты

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:
* сервису платежей (далее - Payment Gate)
* кредитному сервису (далее - Credit Gate)

Приложение должно в собственной СУБД сохранять информацию о том, каким способом был совершён платёж и успешно ли он был совершён (при этом данные карт сохранять не допускается).

## Запуск автотестов
1. Клонировать репозиторий на компьютер
2. Открыть проект в JetBrains IntelliJ IDEA
3. Запустить контейнер командой:
* `docker-compose up -d --build`

4. Перейти в папку artifacts:
* `cd artifacts`

5.1 Запустить приложение с MySQL командой:
* `java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -Dspring.datasource.username=app -Dspring.datasource.password=pass -jar aqa-shop.jar`

5.2 Запустить приложение с PostgreSQL командой:
* `java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -Dspring.datasource.username=app -Dspring.datasource.password=pass -jar aqa-shop.jar`

6.1 Запустить тесты командой `gradlew clean test -Durl="jdbc:mysql://localhost:5432/app" -Duser="app" -Dpassword="pass" --info`

6.2 Запустить тесты командой `gradlew clean test -Durl="jdbc:postgresql://localhost:5432/app" -Duser="app" -Dpassword="pass" --info`

7. Для получения отчета (Allure) использовать команду gradlew allureServe