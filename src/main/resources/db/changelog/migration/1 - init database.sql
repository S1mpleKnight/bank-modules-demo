CREATE TABLE bank_account (
                              id uuid NOT NULL,
                              activity_type smallint NOT NULL,
                              code_type smallint NOT NULL,
                              credit numeric(38,2) NOT NULL,
                              debit numeric(38,2) NOT NULL,
                              number character varying(255) NOT NULL
);

ALTER TABLE bank_account OWNER TO postgres;

CREATE TABLE city (
                      id uuid NOT NULL,
                      name character varying(255) NOT NULL
);

ALTER TABLE city OWNER TO postgres;

CREATE TABLE client (
                        id uuid NOT NULL,
                        birth_place character varying(255) NOT NULL,
                        birthday date NOT NULL,
                        disability_status smallint,
                        email character varying(255),
                        family_status smallint,
                        first_name character varying(255) NOT NULL,
                        home_phone_number character varying(255),
                        is_liable_military_service boolean,
                        is_male boolean NOT NULL,
                        last_name character varying(255) NOT NULL,
                        living_address character varying(255),
                        middle_name character varying(255) NOT NULL,
                        mobile_phone_number character varying(255),
                        monthly_income numeric(38,2),
                        nationality character varying(255),
                        pensioner boolean,
                        living_city_id uuid NOT NULL
);

ALTER TABLE client OWNER TO postgres;

CREATE TABLE deposit (
                         id uuid NOT NULL,
                         contract_number bigint NOT NULL,
                         contract_term integer NOT NULL,
                         currency smallint NOT NULL,
                         deposit_type smallint NOT NULL,
                         end_date date NOT NULL,
                         percent numeric(36,2) NOT NULL,
                         start_date date NOT NULL,
                         sum_amount numeric(38,2) NOT NULL,
                         client_id uuid NOT NULL,
                         current_account_id uuid NOT NULL,
                         percent_account_id uuid NOT NULL
);

ALTER TABLE deposit OWNER TO postgres;

CREATE TABLE passport (
                          id uuid NOT NULL,
                          identity_number character varying(255) NOT NULL,
                          issue_date date NOT NULL,
                          issuing_authority character varying(255) NOT NULL,
                          passport_number character varying(255) NOT NULL,
                          passport_series character varying(255) NOT NULL,
                          client_id uuid NOT NULL
);

ALTER TABLE passport OWNER TO postgres;

ALTER TABLE ONLY bank_account
    ADD CONSTRAINT bank_account_pkey PRIMARY KEY (id);

ALTER TABLE ONLY city
    ADD CONSTRAINT city_pkey PRIMARY KEY (id);

ALTER TABLE ONLY client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);

ALTER TABLE ONLY deposit
    ADD CONSTRAINT deposit_pkey PRIMARY KEY (id);

ALTER TABLE ONLY passport
    ADD CONSTRAINT passport_pkey PRIMARY KEY (id);

ALTER TABLE ONLY passport
    ADD CONSTRAINT fk89xwh3e0lynvw1jts5sg1x49m FOREIGN KEY (client_id) REFERENCES client(id);

ALTER TABLE ONLY deposit
    ADD CONSTRAINT fkdncl06fck15jjqdfl366bao1w FOREIGN KEY (percent_account_id) REFERENCES bank_account(id);

ALTER TABLE ONLY deposit
    ADD CONSTRAINT fkeqfdgr5gh6r730mtsb1ewyxlx FOREIGN KEY (current_account_id) REFERENCES bank_account(id);

ALTER TABLE ONLY deposit
    ADD CONSTRAINT fkey7qgtdvk8nipnlgbuagofnqc FOREIGN KEY (client_id) REFERENCES client(id);


ALTER TABLE ONLY client
    ADD CONSTRAINT fklhboo7ky5xqjr7ue73mbx5d2a FOREIGN KEY (living_city_id) REFERENCES city(id);