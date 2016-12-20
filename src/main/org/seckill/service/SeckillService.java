package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.ClosedSeckillException;
import org.seckill.exception.RepeatedSeckillException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * Created by kophy on 12/13/2016.
 */
public interface SeckillService {
    /**
     * get list of seckill entities
     * @return seckill list
     */
    List<Seckill> getSeckillList();

    /**
     * get seckill entity by id
     * @param seckillId
     * @return seckill entity
     */
    Seckill getById(long seckillId);

    /**
     * expose seckill url
     * @param seckillId id of seckill entity
     * @return corresponding url
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * execute seckill
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatedSeckillException
     * @throws ClosedSeckillException
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatedSeckillException, ClosedSeckillException;

    /**
     * execute seckill procedure
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution excuteSeckillProcedure(long seckillId, long userPhone, String md5);
}
