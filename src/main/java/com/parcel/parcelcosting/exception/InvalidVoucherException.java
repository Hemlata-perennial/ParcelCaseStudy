package com.parcel.parcelcosting.exception;

/**
 * This exception is thrown if the voucher code is invalid
 */
public class InvalidVoucherException extends RuntimeException {
    public InvalidVoucherException(String message) {
        super(message);
    }
}
