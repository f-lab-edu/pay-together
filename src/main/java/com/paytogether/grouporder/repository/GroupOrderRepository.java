package com.paytogether.grouporder.repository;

import com.paytogether.grouporder.entity.GroupOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupOrderRepository extends JpaRepository<GroupOrder, Long> {

}
