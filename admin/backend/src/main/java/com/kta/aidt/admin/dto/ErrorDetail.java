package com.kta.aidt.admin.dto;

public class ErrorDetail {
    private String message;
    private String code;
    private String field;

    public ErrorDetail() {}

    public ErrorDetail(String message, String code) {
        this.message = message;
        this.code = code;
        this.field = null;
    }

    public ErrorDetail(String field, String message, String code) {
        this.field = field;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}