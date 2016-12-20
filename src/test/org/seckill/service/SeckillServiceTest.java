package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.ClosedSeckillException;
import org.seckill.exception.RepeatedSeckillException;
import org.seckill.service.impl.SeckillServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kophy on 12/13/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}", list);
        // Output:
        // list=[
        //  Seckill{seckillId=1000, name='cappuccino', number=98, startTime=Tue Dec 13 00:38:29 PST 2016,
        //      endTime=Fri Dec 23 08:00:00 PST 2016, createTime=Tue Dec 13 00:12:54 PST 2016},
        //  Seckill{seckillId=1001, name='macchiato', number=200, startTime=Mon Dec 12 08:00:00 PST 2016,
        //      endTime=Fri Dec 23 08:00:00 PST 2016, createTime=Tue Dec 13 00:12:54 PST 2016},
        //  Seckill{seckillId=1002, name='mocha', number=300, startTime=Mon Dec 12 08:00:00 PST 2016,
        //      endTime=Fri Dec 23 08:00:00 PST 2016, createTime=Tue Dec 13 00:12:54 PST 2016},
        //  Seckill{seckillId=1003, name='latte', number=400, startTime=Mon Dec 12 08:00:00 PST 2016,
        //      endTime=Fri Dec 23 08:00:00 PST 2016, createTime=Tue Dec 13 00:12:54 PST 2016}]
    }

    @Test
    public void getById() throws Exception {
        long seckillId = 1000L;
        Seckill seckill = seckillService.getById(seckillId);
        logger.info("seckill={}", seckill);
        // Output:
        // seckill=Seckill{seckillId=1000, name='cappuccino', number=98, startTime=Tue Dec 13 00:38:29 PST 2016,
        //      endTime=Fri Dec 23 08:00:00 PST 2016, createTime=Tue Dec 13 00:12:54 PST 2016}
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer: " + exposer);
        // Output:
        // exposer: Exposer{exposed=true, md5='79a2e0d328a4a2ac9b8a96cdc6674787', seckillId=1000,
        //      start=0, end=0, current=0}
    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 1000L;
        long phoneNumber = 13574235323L;
        String md5 = "79a2e0d328a4a2ac9b8a96cdc6674787";
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(id, phoneNumber, md5);
            logger.info("seckillExecution:" + seckillExecution);
        } catch (ClosedSeckillException e) {
            logger.error(e.getMessage());
        } catch (RepeatedSeckillException e) {
            logger.error(e.getMessage());
        }
        // Output:
        // SeckillExecution{seckillId=1000, state=1, stateInfo='seckill success', successKilled=SuccessKilled{seckillId=1000,
        //      userPhone=13574235323, state=0, createTime=Tue Dec 13 08:48:49 PST 2016, seckill=Seckill{seckillId=1000,
        //      name='cappuccino', number=97, startTime=Tue Dec 13 08:48:49 PST 2016, endTime=Fri Dec 23 08:00:00 PST 2016,
        //      createTime=Tue Dec 13 00:12:54 PST 2016}}}
    }

    @Test
    public void testSeckillLogic() throws Exception {
        long id = 1003L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()) {
            logger.info("exposer: " + exposer);
            long phoneNumber = 13574678323L;
            String md5 = exposer.getMd5();

            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, phoneNumber, md5);
                logger.info("seckillExecution:" + seckillExecution);
            } catch (ClosedSeckillException e) {
                logger.error(e.getMessage());
            } catch (RepeatedSeckillException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.info("exposer: " + exposer);
        }
        // seckillExecution:SeckillExecution{seckillId=1003, state=1, stateInfo='seckill success',
        // successKilled=SuccessKilled{seckillId=1003, userPhone=13574678323, state=0,
        // createTime=Sun Dec 18 01:57:13 PST 2016, seckill=Seckill{seckillId=1003, name='latte',
        // number=399, startTime=Sun Dec 18 01:57:13 PST 2016, endTime=Fri Dec 23 08:00:00 PST 2016,
        // createTime=Tue Dec 13 00:12:54 PST 2016}}}
    }

    @Test
    public void testExecuteSeckillProcedure() {
        long seckillId = 1002L;
        long phone = 14343545434L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        System.out.println(exposer);
        if (exposer.isExposed()) {
            String md5 = exposer.getMd5();
            SeckillExecution seckillExecution = seckillService.excuteSeckillProcedure(seckillId, phone, md5);
            logger.info(seckillExecution.getStateInfo());
        }

        // 00:57:48.492 [main] DEBUG o.s.d.S.queryByIdWithSeckill - ==> Parameters: 1002(Long), 14343545434(Long)
        // 00:57:48.513 [main] DEBUG o.s.d.S.queryByIdWithSeckill - <==      Total: 0
        // 00:57:48.518 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2a640157]
        // 00:57:48.519 [main] INFO  o.seckill.service.SeckillServiceTest - seckill success
    }
}