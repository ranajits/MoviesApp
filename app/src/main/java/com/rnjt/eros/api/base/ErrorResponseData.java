package com.rnjt.eros.api.base;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

public class ErrorResponseData {

    @SerializedName("s")
    int isSuccess;


    @SerializedName("m")
    String message;

    int responseCode;

    public int getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(int isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @Expose(serialize = false, deserialize = false)
    private String rawResponse;

    public static ErrorResponseData getFailedToLoadError() {
        ErrorResponseData error = new ErrorResponseData();
        return error;
    }

    public static ErrorResponseData getNoNetworkError() {
        ErrorResponseData error = new ErrorResponseData();
        return error;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
        // getErrorMsg();
    }

    public void getErrorMsg() {
        try {
            JSONObject errorObj = new JSONObject(getRawResponse());
            Type type = new TypeToken<Map<String, String>>() {
            }.getType();
            Map<String, String> map = new Gson().fromJson(errorObj.getString("errors"), type);
            //setErrors(map);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
