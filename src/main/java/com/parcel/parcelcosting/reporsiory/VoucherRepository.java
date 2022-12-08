package com.parcel.parcelcosting.reporsiory;

import com.parcel.parcelcosting.entity.Parcel;
import com.parcel.parcelcosting.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher,Long> {

}
