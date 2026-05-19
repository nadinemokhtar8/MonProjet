package com.nadine.users.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    public Role(Long role_id, String role) {
		super();
		this.role_id = role_id;
		this.role = role;
	}
    public Role() {
    }
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;
    @Column(unique = true)
    private String role;
	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
