package com.example.SpringDemo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringDemo.Entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

	Account findByUserName(String userName);
}
