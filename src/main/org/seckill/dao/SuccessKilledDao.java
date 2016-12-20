package org.seckill.dao;

import org.apache.ibatis.annotations.Param;

import org.seckill.entity.SuccessKilled;

/**
 * Created by kophy on 12/13/2016.
 */
public interface SuccessKilledDao {
    /**
     * generate success seckill log
     * use primary key to avoid repeated seckill
     * @param seckillId
     * @param userPhone
     * @return number of rows affected
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * query success seckill log entity
     * corresponding prodcut is carried in the
     * @param seckillId
     * @param userPhone
     * @return corresponding success seckill log entity
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}