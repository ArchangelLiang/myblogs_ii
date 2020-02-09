package com.gwg.util;

public class RequestResult {

    private String status;
    private String redirectUrl;

    public RequestResult() {
    }

    public RequestResult(String status, String redirectUrl) {
        this.status = status;
        this.redirectUrl = redirectUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public String toString() {
        return "RequestResult{" +
                "status='" + status + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                '}';
    }
}
