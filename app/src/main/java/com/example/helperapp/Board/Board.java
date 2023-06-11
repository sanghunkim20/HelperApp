package com.example.helperapp.Board;

public class Board {
    private String name;
    private String teleNum;
    private String title;
    private String content;
    private String x, y;

    public Board() {
    } // 빈 생성자

    public String getName() {
        return name;
    }

    public String getTeleNum() {
        return teleNum;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setTeleNum(String teleNum) {
        this.teleNum = teleNum;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    // 값을 추가할때 쓰는 함수, ShopAndMenuAddActivity에서 addShop함수 사용할 것임
    public Board(String name, String teleNum, String title, String content,
                 String x, String y) {
        this.name = name;
        this.teleNum = teleNum;
        this.title = title;
        this.content = content;
        this.x = x;
        this.y = y;
    }
}