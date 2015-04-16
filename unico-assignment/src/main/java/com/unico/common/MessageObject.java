package com.unico.common;

import java.io.Serializable;

public class MessageObject implements Serializable{

    private Integer firstInt;
    private Integer secondInt;

    public MessageObject(Integer firstInt, Integer secondInt) {
        this.firstInt = firstInt;
        this.secondInt = secondInt;
    }

    public Integer getFirstInt() {
        return firstInt;
    }

    public Integer getSecondInt() {
        return secondInt;
    }

}
