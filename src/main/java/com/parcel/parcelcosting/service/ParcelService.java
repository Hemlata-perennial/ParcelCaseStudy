package com.parcel.parcelcosting.service;

import com.parcel.parcelcosting.entity.Parcel;

import java.math.BigDecimal;

public interface ParcelService {
    public BigDecimal getCost(Parcel parcel);
}
