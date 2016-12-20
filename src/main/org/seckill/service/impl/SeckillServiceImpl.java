package org.seckill.service.impl;

import org.apache.commons.collections.MapUtils;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.ClosedSeckillException;
import org.seckill.exception.RepeatedSeckillException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.seckill.dao.cache.RedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kophy on 12/13/2016.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SeckillDao seckillDao;

    @Resource
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    private final String salt = "KFJ^&DFDfj(DF6DF5dfo3432*65df";

    /**
     *
     * @return
     */
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 100);
    }

    /**
     *
     * @param seckillId
     * @return
     */
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    /**
     *
     * @param seckillId
     * @return
     */
    public Exposer exportSeckillUrl(long seckillId) {
        /* before optimization
        Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        */

        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            // access database
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null) {
                return new Exposer(false, seckillId);
            } else {
                // put into redis to cache
                redisDao.putSeckill(seckill);
            }
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date currentTime = new Date();

        if (currentTime.getTime() < startTime.getTime() || currentTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, startTime.getTime(), endTime.getTime(), currentTime.getTime());
        }
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    /**
     * TODO：translate exception information
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatedSeckillException
     * @throws ClosedSeckillException
     */
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatedSeckillException, ClosedSeckillException {

        if (md5 == null || (!md5.equals(getMD5(seckillId)))) {
            throw new SeckillException("秒杀数据篡改");
        }

        Date currentTime = new Date();
        try {
            /*
            int updateCount = seckillDao.reduceNumber(seckillId, currentTime);
            if (updateCount <= 0) {
                //没有更新到记录，秒杀结束
                throw new ClosedSeckillException("秒杀已经关闭");
            } else {
                //记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insertCount <= 0) {
                    //重复秒杀
                    throw new RepeatedSeckillException("用户重复秒杀");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
            */
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0) {
                // repeated seckill
                throw new RepeatedSeckillException("Seckill is repeated");
            } else {
                // reduce number
                int updateCount = seckillDao.reduceNumber(seckillId, currentTime);
                if (updateCount <= 0) {
                    throw new ClosedSeckillException("Seckill is closed");
                } else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }

        } catch (ClosedSeckillException e1) {
            throw e1;
        } catch (RepeatedSeckillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new SeckillException("秒杀系统错误" + e.getMessage());
        }
    }

    /**
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    public SeckillExecution excuteSeckillProcedure(long seckillId, long userPhone, String md5) {
        if (md5 == null || (!md5.equals(getMD5(seckillId)))) {
            throw new SeckillException("Seckill data rewrite");
        }
        Date killTime = new Date();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("seckillId", seckillId);
        map.put("phone", userPhone);
        map.put("killTime", killTime);
        map.put("result", null);

        System.out.println("Map size = " + map.size());

        // execute procedure; assign result
        try {
            seckillDao.killByProcedure(map);
            int result = MapUtils.getInteger(map, "result", -2);
            if (result == 1) {
                SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
            } else {
                return new SeckillExecution(seckillId, SeckillStateEnum.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
        }
    }

    /**
     * get md5 value of seckill id
     * @param seckillId
     * @return md5 value
     */
    private String getMD5(long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
