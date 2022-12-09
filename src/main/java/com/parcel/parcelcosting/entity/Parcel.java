package com.parcel.parcelcosting.entity;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Parcel {

    @NotNull
    Double weight;

    @NotNull
    Double height;

    @NotNull
    Double width;

    @NotNull
    Double length;

    public Double getWeight() {
        return weight;
    }


    public Double getHeight() {
        return height;
    }


    public Double getWidth() {
        return width;
    }


    public Double getLength() {
        return length;
    }


    public static BigDecimal getVolume(final Parcel parcel) {
        return BigDecimal.valueOf(parcel.length * parcel.width * parcel.height);
    }

    @Override
    public String toString() {
        return "weight=" + weight +
                ", height=" + height +
                ", width=" + width +
                ", length=" + length;
    }
}
