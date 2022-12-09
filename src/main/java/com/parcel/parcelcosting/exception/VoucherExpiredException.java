package com.parcel.parcelcosting.exception;

/**
 *  This exception is thrown if the voucher is expired
 */
public class VoucherExpiredException extends RuntimeException {
    public VoucherExpiredException(String message) {
        super(message);
    }
}
