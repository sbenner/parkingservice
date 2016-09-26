package com.parking.web.response;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 8/26/16
 * Time: 8:46 PM
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;



public class BaseResponse extends ResponseEntity<Object> {

    public static BaseResponse Ok() {
        return new BaseResponse(HttpStatus.OK);
    }

    public static BaseResponse Created() {
        return new BaseResponse(HttpStatus.CREATED);
    }

    public static BaseResponse response(Object entity) {
        return entity != null ? BaseResponse.Ok(entity) : BaseResponse.fail();
    }

    public static BaseResponse Ok(Object t) {
        return new BaseResponse(t, HttpStatus.OK);
    }

    public static BaseResponse fail(String message) {
        return new BaseResponse(message,HttpStatus.BAD_REQUEST);
    }


    public static BaseResponse fail() {
        return new BaseResponse(HttpStatus.BAD_REQUEST);
    }

    public BaseResponse(HttpStatus statusCode) {
        super(statusCode);
    }

    public BaseResponse(Object body, HttpStatus statusCode) {
        super(body, statusCode);
    }

    public BaseResponse(MultiValueMap<String, String> headers, HttpStatus statusCode) {
        super(headers, statusCode);
    }

    public BaseResponse(String body, MultiValueMap<String, String> headers, HttpStatus statusCode) {
        super(body, headers, statusCode);
    }
}
