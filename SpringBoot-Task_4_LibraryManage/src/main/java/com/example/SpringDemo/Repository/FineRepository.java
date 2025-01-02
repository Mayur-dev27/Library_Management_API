package com.example.SpringDemo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringDemo.Entity.Fine;

@Repository
public interface FineRepository extends JpaRepository<Fine, Integer> {

}
