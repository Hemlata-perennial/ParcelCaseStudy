package com.parcel.parcelcosting.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "voucher_id", nullable = false)
    private Long voucherId;

    @OneToOne(mappedBy = "voucher")
    private Parcel parcel;
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "discount", nullable = false)
    private double discount;

    @Column(name = "discount_cost")
    private double discountCost;

    @Column(name = "voucher_applied", nullable = false)
    private boolean voucherApplied;

    @Column(name = "message")
    private String message;

    @Column(name = "valid_till")
    private String voucherValidTill;

    public Long getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscountCost() {
        return discountCost;
    }

    public void setDiscountCost(double discountCost) {
        this.discountCost = discountCost;
    }

    public boolean isVoucherApplied() {
        return voucherApplied;
    }

    public void setVoucherApplied(boolean voucherApplied) {
        this.voucherApplied = voucherApplied;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVoucherValidTill() {
        return voucherValidTill;
    }

    public void setVoucherValidTill(String voucherValidTill) {
        this.voucherValidTill = voucherValidTill;
    }
}
