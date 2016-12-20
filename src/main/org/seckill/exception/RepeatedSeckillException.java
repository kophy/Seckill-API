package org.seckill.exception;


/**
 * Created by kophy on 12/13/2016.
 */
public class RepeatedSeckillException extends SeckillException {

    public RepeatedSeckillException(String message) {
        super(message);
    }

    public RepeatedSeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
