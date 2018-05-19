package com.example.anujdawar.client;

public class Product {

    int ID;
    String myCommand, dev1, dev2, dev3, dev4, dev5;

    public Product(String myCommand, String dev1, String dev2, String dev3, String dev4, String dev5)
    {
        this.dev1 = dev1;
        this.dev2 = dev2;
        this.dev3 = dev3;
        this.dev4 = dev4;
        this.dev5 = dev5;
        ID++;
        this.myCommand = myCommand;
    }

    public String getDev1() {
        return dev1;
    }

    public void setDev1(String dev1) {
        this.dev1 = dev1;
    }

    public String getDev2() {
        return dev2;
    }

    public void setDev2(String dev2) {
        this.dev2 = dev2;
    }

    public String getDev3() {
        return dev3;
    }

    public void setDev3(String dev3) {
        this.dev3 = dev3;
    }

    public String getDev4() {
        return dev4;
    }

    public void setDev4(String dev4) {
        this.dev4 = dev4;
    }

    public String getDev5() {
        return dev5;
    }

    public void setDev5(String dev5) {
        this.dev5 = dev5;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMyCommand() {
        return myCommand;
    }

    public void setMyCommand(String myCommand) {
        this.myCommand = myCommand;
    }
}
