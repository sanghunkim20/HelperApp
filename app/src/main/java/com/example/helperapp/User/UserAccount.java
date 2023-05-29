package com.example.helperapp.User;

public class UserAccount {
    private String email;
    private String name;
    private String password;
    private String teleNum;
    private String idToken;

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public UserAccount() { } //빈 생성자가 필요 (firebase 관련)

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() { return password;}

    public String getTeleNum() { return teleNum;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTeleNum(String teleNum) {this.teleNum = teleNum;}

    public UserAccount(String email, String name, String teleNum, String idToken) {
        this.email = email;
        this.name = name;
        this.teleNum = teleNum;
        this.idToken = idToken;
    }
}