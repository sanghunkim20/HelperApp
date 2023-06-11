package com.example.helperapp.Shop;

// 파이어베이스 테이블이라고 생각하고, 테이블에 들어 갈 속성 값 넣기
//파이어베이스는 RDBMS와 다르기 때문에 테이블이라는 개념이 없음. 원래는 키값이라고 부름
public class Shop {
    String et_name; // 가게 이름
    String et_telenum; // 운영시간
    String et_title; // 휴무일
    String et_content;  // 전화번호

    public Shop() {
        // Default constructor required for calls to DataSnapshot.getValue(Shop.class)
        // DataSnapshot.getValue를 위한 기본생성자 선언
    }

    // Getter 설정
    public String getEt_name() {
        return et_name;
    }
    public String getEt_telenum() { return et_telenum; }
    public String getEt_title() {
        return et_title;
    }
    public String getEt_content() {
        return et_content;
    }
    // Setter 설정
    public void setEt_name(String et_name) {
        this.et_name = et_name;
    }
    public void setEt_telenum(String et_telenum) {
        this.et_telenum = et_telenum;
    }
    public void setEt_title(String et_title) {
        this.et_title = et_title;
    }
    public void setEt_content(String et_content) {
        this.et_content = et_content;
    }

    // 값을 추가할때 쓰는 함수, ShopAndMenuAddActivity에서 addShop함수 사용할 것임
    public Shop(String et_name, String et_telenum, String et_title, String et_content) {
        this.et_name = et_name;
        this.et_telenum = et_telenum;
        this.et_title = et_title;
        this.et_content = et_content;
    }
}

