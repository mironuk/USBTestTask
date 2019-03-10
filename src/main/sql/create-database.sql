CREATE DATABASE <database_dbname>
    DEFAULT CHARACTER SET utf8
    DEFAULT COLLATE utf8_general_ci;

SET storage_engine = InnoDB;

GRANT ALL PRIVILEGES ON <database_dbname>.*
    TO <database_username>@'%'
    IDENTIFIED BY '<database_password>';
