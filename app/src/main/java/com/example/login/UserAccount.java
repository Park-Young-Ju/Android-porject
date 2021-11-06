package com.example.login;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UserAccount {

    private String emailId;     // 이메일 아이디
    private String userName;     // 이름
    private String password;    // 비밀번호
    private String phone;       // 전화번호
    private String time;        // 시간
    private String birth;        // 시간
    private String idToken;

    //Firebase 사용할떄 빈 생성자 꼭 만들기
    public UserAccount() { }

    //Getter Setter

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {

        this.time = time;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public String getEmailId() {

        return emailId;
    }

    public void setEmailId(String emailId) {

        this.emailId = emailId;
    }

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {

        this.phone = phone;
    }

    public String getIdToken() {

        return idToken;
    }

    public void setIdToken(String idToken) {

        this.idToken = idToken;
    }

    public UserAccount(String emailId, String userName, String password, String phone, String birth){
        this.emailId = emailId;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.birth = birth;
        this.time = time;
        this.idToken = idToken;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("emailId", emailId);
        result.put("userName", userName);
        result.put("password", password);
        result.put("phone", phone);
        result.put("birth", birth);

        return result;
    }

}
