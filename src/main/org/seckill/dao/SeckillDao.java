package org.seckill.dao;

import org.apache.ibatis.annotations.Param;

import org.seckill.entity.Seckill;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kophy on 12/13/2016.
 */
public interface SeckillDao {
    /**
     * try to decrease the number of available products
     * @param seckillId
     * @param killTime
     * @return  number of row affected
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * query seckill entity by id
     * @param seckillId
     * @return corresponding seckill entity
     */
    Seckill queryById(long seckillId);

    /**
     * query all seckill entities
     * @param offset
     * @param limit
     * @return list of all seckill entities
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * make seckill with MySQL procedure
     * @param paramMap
     */
    void killByProcedure(Map<String, Object> paramMap);
}
