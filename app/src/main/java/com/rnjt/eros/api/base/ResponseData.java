package com.rnjt.eros.api.base;

import com.google.gson.annotations.SerializedName;

public class ResponseData {
    @SerializedName("s")
    int isSuccess;


    @SerializedName("m")
    String message;

    int responseCode;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public int getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(int isSuccess) {
        this.isSuccess = isSuccess;
    }
}
