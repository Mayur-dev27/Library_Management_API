package com.example.SpringDemo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringDemo.Entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
