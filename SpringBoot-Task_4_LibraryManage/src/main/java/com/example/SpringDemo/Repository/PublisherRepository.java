package com.example.SpringDemo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringDemo.Entity.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

}
