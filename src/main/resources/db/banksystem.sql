CREATE TABLE banks_list 
(
    bankId               BIGINT AUTO_INCREMENT NOT NULL,
    bankName             varchar(50) NOT NULL UNIQUE,
    maxTransfer          BIGINT      NOT NULL,
    maxDeposit           BIGINT      NOT NULL,
    maxWithdraw          BIGINT      NOT NULL,
    maxTotalLoans        BIGINT      NOT NULL,
    requireYearsCountry  INT         NOT NULL,
    contactNumber        varchar(16) NOT NULL UNIQUE,
    PRIMARY KEY (bankId)
);

CREATE TABLE bank_users
(
    userName      varchar(50) NOT NULL,
    email         varchar(50) NOT NULL UNIQUE,
    userId        BIGINT AUTO_INCREMENT NOT NULL,
    userLogin     varchar(50) NOT NULL UNIQUE,
    userPassword  varchar(16) NOT NULL,
    yearsCountry  INT         NOT NULL,
    employed      INT         NOT NULL,
    totalLoans    BIGINT      NOT NULL,
    userBalance   BIGINT      NOT NULL,
    contactNumber varchar(16) NOT NULL UNIQUE,
    isAdmin       INT         NOT NULL,
    PRIMARY KEY (userId)
);

CREATE TABLE loans_history (
    loanId   BIGINT AUTO_INCREMENT NOT NULL,
    userId   BIGINT NOT NULL,
    bankId   BIGINT NOT NULL,
    bankName varchar(50) NOT NULL,
    cashSum  BIGINT NOT NULL,
    PRIMARY KEY (loanId)
);

CREATE TABLE roles (
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name varchar(50) NOT NULL,
    PRIMARY key (id)
);

ALTER TABLE loans_history
    ADD CONSTRAINT loans_history_userId_fk FOREIGN KEY (userId) REFERENCES bank_users(userId) ON DELETE NO ACTION;

CREATE INDEX loans_history_userId_fk ON loans_history(userId);

ALTER TABLE loans_history
    ADD CONSTRAINT loans_history_bankId_fk FOREIGN KEY (bankId) REFERENCES banks_list(bankId) ON DELETE NO ACTION;

CREATE INDEX loans_history_bankId_fk ON loans_history(bankId);