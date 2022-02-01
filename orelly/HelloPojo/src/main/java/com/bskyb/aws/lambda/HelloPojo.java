package com.bskyb.aws.lambda;

public class HelloPojo {
    public String handler(String s) {
        return "Hello, " + s;
    }
}