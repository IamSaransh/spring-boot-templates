package com.saransh.SpringRest.HelloWorld.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

public class HelloWorldBean {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    public HelloWorldBean(){}
    public HelloWorldBean(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "HelloWorldBean{" +
                "message='" + message + '\'' +
                '}';
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
