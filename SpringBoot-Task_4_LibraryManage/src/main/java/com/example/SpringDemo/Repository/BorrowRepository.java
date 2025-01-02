package com.example.SpringDemo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.SpringDemo.Entity.Borrow;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Integer> {

//
//    @Query("SELECT b FROM Borrow b WHERE b.dueDate < CURRENT_DATE AND b.returned = false") 
//    List<Borrow> findOverdueBorrows(); // Custom query to find overdue borrow recordsO
	
    @Query("SELECT b FROM Borrow b " +
            "JOIN FETCH b.user u " +      // Fetch the associated User
            "JOIN FETCH b.book bk " +     // Fetch the associated Book
            "WHERE b.dueDate < CURRENT_DATE AND b.isReturned = false") // Corrected the field name
     List<Borrow> findOverdueBorrows(); // Custom query to find overdue borrow records
}
