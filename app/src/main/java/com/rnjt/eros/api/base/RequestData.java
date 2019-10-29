package com.rnjt.eros.api.base;

import com.google.gson.annotations.SerializedName;

public class RequestData {

    @SerializedName("data")
    Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
