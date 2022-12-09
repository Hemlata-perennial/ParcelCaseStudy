package com.parcel.parcelcosting.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Instance of this class is sent as a response if any exception occurred
 */
@Getter
public class ExceptionResponse{
    @Setter
    String error;
    final Date timestamp;
    public ExceptionResponse(String error) {
        this.error = error;
        timestamp = new Date();
    }
}
