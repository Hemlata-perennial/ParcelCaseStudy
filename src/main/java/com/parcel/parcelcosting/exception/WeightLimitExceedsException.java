package com.parcel.parcelcosting.exception;

/**
 * This exception is thrown if the voucher code is invalid
 */
public class WeightLimitExceedsException extends RuntimeException {
    public WeightLimitExceedsException(String message) {
        super(message);
    }
}
