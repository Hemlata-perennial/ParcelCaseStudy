package com.parcel.parcelcosting.entity;

import jdk.nashorn.internal.objects.annotations.Getter;

import javax.persistence.*;

@Entity
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "parcel_id", nullable = false)
    private Long parcelId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "voucher_id", referencedColumnName = "voucher_id")
    private Voucher voucher;


    @Column(name = "height", nullable = false)
    private Double height;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "width", nullable = false)
    private Double width;

    @Column(name = "length", nullable = false)
    private Double length;

    @Column(name = "volume", nullable = false)
    private Double volume;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "rule")
    private String rule;

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
    public Double getVolume() {
        return volume;
    }
    public void setVolume(Double volume) {
        this.volume = volume;
    }
    public Double getLength() {
        return length;
    }
    public Double getHeight() { return height; }
    public Double getWeight() { return weight;}
    public Double getWidth() { return width;}

}
