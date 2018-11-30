1. ЗАПУСК И ТЕСТИРОВАНИЕ ПРИЛОЖЕНИЯ.
    1.1 Для выполнения юнит- и интеграционных тестов - команда mvn clean install -P=integration
    1.2 Для разворачивания приложения на сервере - команда mvn cargo:run
        Доступ к приложению по адресу http://localhost:8080/onlineShop/


2. ОПИСАНИЕ ПРОЕКТА
Пользователи приложения могут находиться в одном из трех возможных состояний:
-ADMIN
-ACTIVE
-BLOCKED

После старта приложения,в базе данных будет находиться четыре пользователя

Администратор Admin:
email - admin@gmail.com
password - admin

Активный пользователь John Doe:
email - active@gmail.com
password - 1234

Заблокированный пользователь Dr. Evil:
email - blocked@gmail.com
password - 1234

Активный пользователь Spiderman:
email - spider@gmail.com
password - 1234