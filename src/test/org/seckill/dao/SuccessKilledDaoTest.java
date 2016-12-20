package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.annotation.SystemProfileValueSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by kophy on 12/13/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        long id = 1000L;
        long phone = 13544356432L;
        int updateCount = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println(updateCount);
        // Output:
        // run first time - 1
        // run later - 0
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long id = 1000L;
        long phone = 13544356432L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
        // Output:
        // SuccessKilled{seckillId=1000, userPhone=13544356432, state=0, createTime=Tue Dec 13 01:02:23 PST 2016,
        //      seckill=Seckill{seckillId=1000, name='cappuccino', number=98, startTime=Tue Dec 13 00:38:29 PST 2016,
        //      endTime=Fri Dec 23 08:00:00 PST 2016, createTime=Tue Dec 13 00:12:54 PST 2016}}
        // Seckill{seckillId=1000, name='cappuccino', number=98, startTime=Tue Dec 13 00:38:29 PST 2016,
        //      endTime=Fri Dec 23 08:00:00 PST 2016, createTime=Tue Dec 13 00:12:54 PST 2016}
    }

}