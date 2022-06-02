package com.shahinsha.shootmovements.model;

public class Data {
    private int Id;

    private String Name;

    private String Email;

    private String Token;

    public void setId(int Id){
        this.Id = Id;
    }
    public int getId(){
        return this.Id;
    }
    public void setName(String Name){
        this.Name = Name;
    }
    public String getName(){
        return this.Name;
    }
    public void setEmail(String Email){
        this.Email = Email;
    }
    public String getEmail(){
        return this.Email;
    }
    public void setToken(String Token){
        this.Token = Token;
    }
    public String getToken(){
        return this.Token;
    }
}

