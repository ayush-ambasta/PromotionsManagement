package com.example.promonade.repositories;

import com.example.promonade.models.Behaviour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BehaviourRepository extends JpaRepository<Behaviour,Long> {

    @Query("select sum(b.LoginFrequency) from Behaviour b where Date(b.lastLoginTime) between :startDate and :endDate")
    Object countLoginsInPeriod(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    @Query("select sum(b.LoginFrequency) as logins, Date(b.lastLoginTime) as date from Behaviour b group by Date(b.lastLoginTime) having Date(b.lastLoginTime) between :startDate and :endDate")
    List<Object[]> countLoginsByDate(@Param("startDate")Date startDate, @Param("endDate")Date endDate);
}
