package com.example.SpringDemo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringDemo.Entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
