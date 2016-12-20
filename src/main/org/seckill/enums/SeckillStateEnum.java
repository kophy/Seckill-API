package org.seckill.enums;

/**
 * Created by kophy on 12/13/2016.
 */
public enum SeckillStateEnum {

    /**
     * seckill execution state and prompt message
     */
    SUCCESS(1,       "seckill success"),
    END(0,           "seckill ends"),
    REPEAT_KILL(-1,  "repeated seckill"),
    INNER_ERROR(-2,  "inner error"),
    DATA_REWRITE(-3, "tampered data");

    private int state;

    private String stateInfo;

    SeckillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    /**
     * get seckill execution state from return code
     * @param index return code
     * @return state
     */
    public static SeckillStateEnum stateOf(int index) {
        for (SeckillStateEnum state: values()) {
            if(state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
