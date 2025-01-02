package com.example.SpringDemo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringDemo.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
