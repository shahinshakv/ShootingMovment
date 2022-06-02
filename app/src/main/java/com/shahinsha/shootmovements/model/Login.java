package com.shahinsha.shootmovements.model;

public class Login {
    private int code;

    private String message;

    private Data data;

    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setData(Data data){
        this.data = data;
    }
    public Data getData(){
        return this.data;
    }
}