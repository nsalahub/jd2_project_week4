DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Items;
DROP TABLE IF EXISTS Role;
CREATE TABLE IF NOT EXISTS Role
(
  id            BIGINT AUTO_INCREMENT          NOT NULL PRIMARY KEY,
  name          VARCHAR(60)                    NOT NULL
);
CREATE TABLE IF NOT EXISTS Users
(
  id            BIGINT AUTO_INCREMENT          NOT NULL PRIMARY KEY,
  username      VARCHAR(60)                    NOT NULL,
  password      VARCHAR(128)                   NOT NULL,
  role_id       BIGINT                         NOT NULL, FOREIGN KEY (role_id) REFERENCES Role(id)
);
CREATE TABLE IF NOT EXISTS I
(
  id            BIGINT AUTO_INCREMENT          NOT NULL PRIMARY KEY,
  name          VARCHAR(60)                    NOT NULL,
  status        VARCHAR(60)                    NOT NULL,
  deleted       BOOLEAN                        DEFAULT  FALSE
);
INSERT INTO  role VALUES (1,'ADMINISTRATOR');
INSERT INTO  role VALUES (2,'CUSTOMER');
INSERT INTO  users VALUES (1,'root','$2a$10$tEHlqzb1MOg1J5UxFNReDeGj0spqhd5UoAS8s9fHlveKKoGJWNwq.',1);
INSERT INTO  users VALUES (2,'customer','$2a$10$UvZf9O7kuY8qpXRAKuvLee8AKfFOSMDmwxZGZOHRttH4MFVatXID2',2);