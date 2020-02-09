package com.gwg.long_var;

public enum BlogFlag {

    ORIGINAL(1),TRANSSHIPMENT(2),TRANSLATE(3);

    private int id;

    BlogFlag(int id) {
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

}