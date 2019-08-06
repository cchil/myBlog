package com.cchilei.blog.pojo;

import java.io.Serializable;

public class Admin implements Serializable,Cloneable{
    private String accountId;

    private String name;

    private String head;

    private String sign;

    private String password;

    public Admin(String accountId, String name, String head, String sign, String password) {
        this.accountId = accountId;
        this.name = name;
        this.head = head;
        this.sign = sign;
        this.password = password;
    }

    public Admin() {
        super();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head == null ? null : head.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

   public Admin clonAdmin(){
       try {
           return (Admin) clone();
       } catch (CloneNotSupportedException e) {
           e.printStackTrace();
       }
       return null;
   }

    @Override
    public String toString() {
        return "Admin{" +
                "accountId='" + accountId + '\'' +
                ", name='" + name + '\'' +
                ", head='" + head + '\'' +
                ", sign='" + sign + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
