package com.example.android_projekt.events;

public class MyEventError {
    int errorCode;
    String text;

    public MyEventError(int errorCode, String text) {
        this.errorCode = errorCode;
        this.text = text;
    }

    @Override
    public String toString() {
        return "MyEventError{" +
                "errorCode=" + errorCode +
                ", error='" + text +
                '}';
    }
}
