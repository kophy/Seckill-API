-- initialize MySQL database
CREATE DATABASE SECKILL;
USE SECKILL;

-- create seckill table
CREATE TABLE seckill (
    `seckill_id`  BIGINT       NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(128) NOT NULL,
    `number`      INT          NOT NULL,
    `start_time`  TIMESTAMP    NOT NULL,
    `end_time`    TIMESTAMP    NOT NULL,
    `create_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (seckill_id),

    KEY idx_start_time(start_time),
    KEY idx_end_time(end_time),
    KEY idx_create_time(create_time)
) ENGINE = InnoDB AUTO_INCREMENT = 1000 DEFAULT CHARSET = utf8;

INSERT INTO seckill(name, number, start_time, end_time)
VALUES
    ('cappuccino', '100', '2016-12-13 00:00:00', '2016-12-24 00:00:00'),
    ('macchiato',  '200', '2016-12-13 00:00:00', '2016-12-24 00:00:00'),
    ('mocha',      '300', '2016-12-13 00:00:00', '2016-12-24 00:00:00'),
    ('latte',      '400', '2016-12-13 00:00:00', '2016-12-24 00:00:00')
;

-- create seckill success table
CREATE TABLE success_killed (
    `seckill_id`  BIGINT    NOT NULL,
    `user_phone`  BIGINT    NOT NULL,
    `state`       TINYINT   NOT NULL DEFAULT -1 COMMENT 'status：-1 invalid，0 success，1 paid',
    `create_time` TIMESTAMP NOT NULL,

    PRIMARY KEY (seckill_id, user_phone),

    KEY idx_create_time(create_time)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;