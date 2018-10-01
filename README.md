Users of application might be in one of three possible states:
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