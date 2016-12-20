package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by kophy on 12/18/2016.
 */
public class RedisDao {
    // redis connection pool
    private JedisPool jedisPool;

    // time length a key can live
    private int timeout = 60 * 60;

    // create schema for protostuff through reflection
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * initialize redis connection pool
     * @param ip
     * @param port
     */
    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    /**
     * get seckill entity from cache
     * @param seckillId id of seckill entity
     * @return deserialized seckill entity
     */
    public Seckill getSeckill(long seckillId) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;                    // 1. get serialized object
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    Seckill seckill = schema.newMessage();              // 2. get empty object
                    ProtobufIOUtil.mergeFrom(bytes, seckill, schema);   // 3. deserialize
                    return seckill;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * put seckill entity in cache
     * @param seckill seckill entity
     * @return serialized seckill entity
     */
    public String putSeckill(Seckill seckill) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckill.getSeckillId();               // 1. get key
                byte[] bytes = ProtobufIOUtil.toByteArray(seckill, schema,      // 2. serialize object
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                String result = jedis.setex(key.getBytes(), timeout, bytes);    // 3. put pair in redis (live one hour)
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
