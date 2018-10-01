package com.whoiszxl.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whoiszxl.order.dataobject.OrderDetail;

/**
 * Created by 廖师兄
 * 2017-12-10 16:12
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
}
