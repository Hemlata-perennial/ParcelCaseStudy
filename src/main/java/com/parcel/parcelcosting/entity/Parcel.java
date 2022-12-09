package com.parcel.parcelcosting.entity;

import com.parcel.parcelcosting.enums.MessageCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

import static com.parcel.parcelcosting.enums.MessageCode.*;

public class Parcel {

    /**
     * weight - represents the weight of the parcel in KGs
     */
    @NotNull(message = INVALID_WEIGHT_NULL)
    @PositiveOrZero(message = INVALID_WEIGHT)
    @Max(value = 50, message = WEIGHT_LIMIT_EXCEEDED)
    Double weight;

    /**
     * height - represents the height of the parcel in CMs
     */
    @NotNull(message = INVALID_HEIGHT_NULL)
    @PositiveOrZero(message = INVALID_HEIGHT)
    Double height;

    /**
     * width - represents the width of the parcel in cms
     */

    @NotNull(message = INVALID_WIDTH_NULL)
    @PositiveOrZero(message = INVALID_WIDTH)
    Double width;

    /**
     * weight - represents the length of the parcel in CMs
     */
    @NotNull(message = INVALID_LENGTH_NULL)
    @PositiveOrZero(message = INVALID_LENGTH)
    Double length;

    public Parcel(double v, double v1, double v2, double v3) {
    }


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
