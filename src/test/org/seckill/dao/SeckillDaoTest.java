package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import org.seckill.entity.Seckill;

import static org.junit.Assert.*;

/**
 * Created by kophy on 12/13/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    @Resource
    private SeckillDao seckillDao;

    @Test
    public void testReduceNumber() throws Exception {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L, killTime);
        System.out.println(updateCount);
        // Output:
        // run first time - 1
        // run later - 0
    }

    @Test
    public void testQueryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
        // Output:
        // Seckill{seckillId=1000, name='cappuccino', number=100, startTime=Mon Dec 12 08:00:00 PST 2016,
        //      endTime=Fri Dec 23 08:00:00 PST 2016, createTime=Tue Dec 13 00:12:54 PST 2016}
    }

    @Test
    public void testQueryAll() throws Exception {
        List<Seckill> seckillList = seckillDao.queryAll(0, 100);
        for(Seckill seckill : seckillList){
            System.out.println(seckill);
        }
        // Output:
        // Seckill{seckillId=1000, name='cappuccino', number=100, startTime=Mon Dec 12 08:00:00 PST 2016,
        //      endTime=Fri Dec 23 08:00:00 PST 2016, createTime=Tue Dec 13 00:12:54 PST 2016}
        // Seckill{seckillId=1001, name='macchiato', number=200, startTime=Mon Dec 12 08:00:00 PST 2016,
        //      endTime=Fri Dec 23 08:00:00 PST 2016, createTime=Tue Dec 13 00:12:54 PST 2016}
        // Seckill{seckillId=1002, name='mocha', number=300, startTime=Mon Dec 12 08:00:00 PST 2016,
        //      endTime=Fri Dec 23 08:00:00 PST 2016, createTime=Tue Dec 13 00:12:54 PST 2016}
        // Seckill{seckillId=1003, name='latte', number=400, startTime=Mon Dec 12 08:00:00 PST 2016,
        //      endTime=Fri Dec 23 08:00:00 PST 2016, createTime=Tue Dec 13 00:12:54 PST 2016}
    }
}