DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS AuditItem;
CREATE TABLE IF NOT EXISTS Item
(
  id            BIGINT AUTO_INCREMENT          NOT NULL PRIMARY KEY,
  name          VARCHAR(63)                    NOT NULL,
  status        ENUM ('READY','COMPLETED')     NOT NULL
);
CREATE TABLE IF NOT EXISTS AuditItem
(
  id            BIGINT AUTO_INCREMENT          NOT NULL PRIMARY KEY,
  `action`      ENUM ('UPDATE','CREATED')      NOT NULL,
  item_id       BIGINT                         NOT NULL,
  `date`        DATETIME                       NOT NULL
);