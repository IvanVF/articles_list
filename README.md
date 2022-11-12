# articles_list
This application use PostgreSQL database with name db_articles_list.

Default datasource username and password "postgres" 1 (can be changed in application.properties).

This application use Liquibase migrations to initialise basic tables and fill them by testing values.

This application starts at port 8342 (can be changed in application.properties).

To check is application starts up use endpoint http://localhost:8342/

This application use bearer tokens for authentication. Credentials for admin: "superadmin" "100". Standard login endpoint http://localhost:8342/auth/login