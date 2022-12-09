package com.parcel.parcelcosting.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
public class Voucher {
    String code;
    Float discount;
    Date expiry;
}
