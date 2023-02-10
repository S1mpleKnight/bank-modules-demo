ALTER TABLE bank_account
    ADD column client_data varchar(255);

ALTER TABLE deposit
    ADD column is_open boolean default true;

CREATE TABLE imaginary_time
(
    id          uuid primary key,
    current_date_value date not null
);