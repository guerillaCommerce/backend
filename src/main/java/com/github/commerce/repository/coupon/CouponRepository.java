package com.github.commerce.repository.coupon;

import com.github.commerce.entity.Coupon;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;


@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    //비관적 락
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Coupon findCouponById(Long couponId);

    boolean existsByTitleAndIsDeleted(String title, boolean isDeleted);
}
