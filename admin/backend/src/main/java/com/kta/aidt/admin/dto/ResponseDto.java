package com.kta.aidt.admin.dto;

import java.util.List;

public class ResponseDto<T> {
    private boolean success;
    private T data;
    private List<ErrorDetail> errors;

    public ResponseDto() {}

    public ResponseDto(boolean success, T data, List<ErrorDetail> errors) {
        this.success = success;
        this.data = data;
        this.errors = errors;
    }

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, null);
    }

    public static <T> ResponseDto<T> error(List<ErrorDetail> errors) {
        return new ResponseDto<>(false, null, errors);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }
}