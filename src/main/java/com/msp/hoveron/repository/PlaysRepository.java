package com.msp.hoveron.repository;

import com.msp.hoveron.entity.Plays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaysRepository extends JpaRepository<Plays, Long> {
}

