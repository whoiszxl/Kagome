package com.whoiszxl.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whoiszxl.order.dataobject.OrderMaster;

/**
 * Created by 廖师兄
 * 2017-12-10 16:11
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
}
