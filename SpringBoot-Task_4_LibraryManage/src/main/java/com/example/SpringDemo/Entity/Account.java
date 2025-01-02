package com.example.SpringDemo.Entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accId")
	private Long accId;
	
	@Column(name = "userName")
	private String userName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name="isBlocked")
	private boolean isBlocked;
	
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
      name = "account_role", 
      joinColumns = @JoinColumn(name = "account_id"), 
      inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    
    public boolean getIsBlocked() { // Custom getter
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked) { // Setter
        this.isBlocked = isBlocked;
    }

}
