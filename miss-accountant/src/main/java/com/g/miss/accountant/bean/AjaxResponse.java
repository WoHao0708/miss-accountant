package com.g.miss.accountant.bean;

import com.g.miss.accountant.util.JsonUtils;

public class AjaxResponse {

    private Integer status;
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString (){
        return JsonUtils.toJson(this);
    }
}
