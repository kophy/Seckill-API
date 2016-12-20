package org.seckill.exception;

/**
 * Created by kophy on 12/13/2016.
 */
public class ClosedSeckillException extends SeckillException {

    public ClosedSeckillException(String message) {
        super(message);
    }

    public ClosedSeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
