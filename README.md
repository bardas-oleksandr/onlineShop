1. СТАРТ ПРИЛОЖЕНИЯ.
В проекте используется зависимость javax.el-api version 3.0.0
А плагин tomcat7 использует ту же зависимость но version 2.2
Поэтому использование плагина tomcat7 для старта этого приложения представляет из себя
нецелесообразно сложную задачу.
Вместо этого, можно использовать плагин cargo-maven2-plugin
Для старта приложения - выполнить команду cargo:run

2. ОПИСАНИЕ ПРОЕКТА
Users of application might have one of three possible states:
-ADMIN
-ACTIVE
-BLOCKED

Different user states provide different rights during using of application.
After mvn liquibase:update is executed, application database will
contain three users - one for each orderState.

Admin credentials:
email - admin@gmail.com
password - admin

Active user credentials:
email - active@gmail.com
password - 1234

Blocked user credentials:
email - blocked@gmail.com
password - 1234