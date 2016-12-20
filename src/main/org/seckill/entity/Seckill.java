package org.seckill.entity;

import java.util.Date;

/**
 * Created by kophy on 12/13/2016.
 */

public class Seckill {
    /**
     * data members corresponding to fields in seckill table
     */
    private long seckillId;     // seckill_id: BIGINT

    private String name;        // name: VARCHAR(128)

    private int number;         // number: INT

    private Date startTime;     // start_time: TIMESTAMP

    private Date endTime;       // end:time: TIMESTAMP

    private Date createTime;    // create_time: TIMESTAMP

    /**
     * print info in unit test
     */
    @Override
    public String toString() {
        return "Seckill{" +
                "seckillId=" + seckillId +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
