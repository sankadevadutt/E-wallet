package com.paytm.app;

public class usersecondhelper {
    String cardnum,expdate,cvv,name,balance;
    public usersecondhelper() {
    }

    public usersecondhelper(String cardnum, String expdate, String cvv, String name, String balance) {
        this.cardnum = cardnum;
        this.expdate = expdate;
        this.cvv = cvv;
        this.name = name;
        this.balance = balance;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public String getExpdate() {
        return expdate;
    }

    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
