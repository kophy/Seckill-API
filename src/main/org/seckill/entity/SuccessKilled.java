package org.seckill.entity;

import java.util.Date;

/**
 * Created by kophy on 12/13/2016.
 */
public class SuccessKilled {
    /**
     * data members corresponding to fields in success_killed table
     */
    private long seckillId;     // seckill_id: BIGINT

    private long userPhone;     // user_phone: BIGINT

    private short state;        // start: TINYINT

    private Date createTime;    // create_time: TIMESTAMP

    /**
     * indicate the killed product
     */
    private Seckill seckill;

    /**
     * print info in unit test
     */
    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                ", seckill=" + seckill +
                '}';
    }

    /**
     * Getter and Setter generated with Intellij
     */
    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

}
