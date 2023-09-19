package com.rcq.rcqauth.util;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private Integer code;
    private HttpStatus httpStatus;
    private String message;
    private Integer count;
    private List<Object> result;
    private void setApiResonse(Integer code, HttpStatus httpStatus, String message){
        this.setCode(code);
        this.setHttpStatus(httpStatus);
        this.setMessage(message);
    }
    public void setSuccessResonse(){

        this.setCode(ApiResponseEnum.OK.getCode());
        this.setHttpStatus(ApiResponseEnum.OK.getHttpStatus());
        this.setMessage("success");
    }
    public void setFAILResonse(String message){

        this.setCode(ApiResponseEnum.FAIL.getCode());
        this.setHttpStatus(ApiResponseEnum.FAIL.getHttpStatus());
        this.setMessage(message);
    }

    public void setINTERNAL_SERVER_ERRORResonse(String message){

        this.setCode(ApiResponseEnum.INTERNAL_SERVER_ERROR.getCode());
        this.setHttpStatus(ApiResponseEnum.INTERNAL_SERVER_ERROR.getHttpStatus());
        this.setMessage(message);
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Object> getResult() {
        return result;
    }

    public void setResult(List<Object> result) {
        this.result = result;
    }

}
