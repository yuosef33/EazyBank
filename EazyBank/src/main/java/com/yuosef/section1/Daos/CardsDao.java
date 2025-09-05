package com.yuosef.section1.Daos;

import com.yuosef.section1.Models.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsDao extends JpaRepository<Cards, Long> {

    List<Cards> findByCustomerId(long customerId);
}
