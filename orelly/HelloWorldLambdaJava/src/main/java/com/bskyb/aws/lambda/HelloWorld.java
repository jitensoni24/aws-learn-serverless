package com.bskyb.aws.lambda;

public class HelloWorld {

    public PojoResponse handlerPojo(PojoRequest request) {

        return new PojoResponse("hello " + request.getS());
    }

    public static class PojoRequest {
        private String s;

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }
    }

    public static class PojoResponse {
        private final String d;

        public PojoResponse(String d) {
            this.d = d;
        }

        public String getD() {
            return d;
        }
    }
}