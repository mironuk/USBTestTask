CREATE TABLE client (
    client_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(128) NOT NULL,
    last_name VARCHAR(128) NOT NULL,
    middle_name VARCHAR(128) NOT NULL,
    inn CHAR(10) NOT NULL,
    PRIMARY KEY(client_id)
);

CREATE TABLE transaction (
    transaction_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    client_id INT UNSIGNED NOT NULL,
    place VARCHAR(128) NOT NULL,
    amount DECIMAL(14,2) NOT NULL,
    currency CHAR(3) NOT NULL,
    card VARCHAR(19),
    PRIMARY KEY(transaction_id)
);
