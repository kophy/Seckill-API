-- procedure of seckill execution
DELIMITER $$

-- row_count(): number of rows affected by last update operation
-- row_count: 0: no change made; >0: row_count rows are changed <0: sql error/not executed

-- begin of definition
CREATE PROCEDURE `seckill`.`execute_seckill`(IN v_seckill_id BIGINT, IN v_phone BIGINT,
    IN v_kill_time TIMESTAMP, INOUT r_result int)
  BEGIN
    DECLARE insert_count INT DEFAULT 0;
    START TRANSACTION ;
    INSERT IGNORE INTO success_killed
    (seckill_id, user_phone, state, create_time)
      VALUES (v_seckill_id, v_kill_time, v_phone, v_kill_time);     -- seckill id should be provided explicitly.
    SELECT row_count() INTO insert_count;
    IF (insert_count = 0) THEN
      ROLLBACK ;
      SET r_result = -1;
    ELSEIF (insert_count < 0) THEN
      ROLLBACK ;
      SET r_result = -2;
    ELSE
      UPDATE seckill
        SET number = number -1
      WHERE seckill_id = v_seckill_id AND
      end_time > v_kill_time AND
      start_time < v_kill_time AND
      number > 0;
      SELECT row_count() into insert_count;
      IF (insert_count = 0) THEN
        ROLLBACK;
        SET r_result = 0;
      ELSEIF (insert_count < 0) THEN
        ROLLBACK;
        SET r_result = -2;
      ELSE
        COMMIT;
        SET r_result = 1;
      END IF;
    END IF;
  END;
$$
-- end of procedure definition

-- a simple test
DELIMITER ;
set @r_result = -3;
CALL execute_seckill(1003, 12345678900, now(), @r_result);
SELECT @r_result;