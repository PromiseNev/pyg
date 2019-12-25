package com.sugar.report.bean;

import java.io.Serializable;

/**
 * 创建时间:   2019/12/25
 *
 * @author ZhangQing
 * 功能描述:
 */

public class Message implements Serializable {
    private static final long serialVersionUID = -5816530547810248598L;
    //消息的次数
    private Long count;
    // 消息的时间
    private Long timeStamp;
    //消息体
    private String message;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" + "count=" + count + ", timeStamp=" + timeStamp + ", message='" + message + '\'' + '}';
    }
}
