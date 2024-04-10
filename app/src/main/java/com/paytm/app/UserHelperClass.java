package com.paytm.app;

public class UserHelperClass {
    String name,pswd,email,phone;
    int wallet_bal;

    public UserHelperClass() {
    }

    public int getWallet_bal() {
        return wallet_bal;
    }

    public void setWallet_bal(int wallet_bal) {
        this.wallet_bal = wallet_bal;
    }

    public UserHelperClass(String name, String pswd, String email, String phone, int wallet_bal) {
        this.name = name;
        this.pswd = pswd;
        this.email = email;
        this.phone = phone;
        this.wallet_bal = wallet_bal;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
