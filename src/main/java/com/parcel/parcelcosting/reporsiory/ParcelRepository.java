package com.parcel.parcelcosting.reporsiory;

import com.parcel.parcelcosting.entity.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelRepository extends JpaRepository<Parcel,Long> {

}
