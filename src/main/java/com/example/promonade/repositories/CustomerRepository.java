package com.example.promonade.repositories;

import com.example.promonade.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT COUNT(c) FROM Customer c JOIN c.loginTimes lt WHERE lt BETWEEN :startDate AND :endDate")
    long countLoginsInPeriod(Date startDate, Date endDate);

    @Query("SELECT DATE(lt) AS date, COUNT(c) AS count FROM Customer c JOIN c.loginTimes lt WHERE lt BETWEEN :startDate AND :endDate GROUP BY DATE(lt) ORDER BY DATE(lt)")
    List<Object[]> countLoginsByDate(Date startDate, Date endDate);
}
