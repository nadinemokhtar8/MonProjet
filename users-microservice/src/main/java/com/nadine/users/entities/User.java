package com.nadine.users.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(unique = true)
    private String username;
    private String password;
    private Boolean enabled;
    public User() {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.email = email;
		this.verificationCode = verificationCode;
		this.codeCreationDate = codeCreationDate;
		this.roles = roles;
	}

	public User(Object object, String string, String string2, boolean b, String string3, Object object2, Object object3,
			ArrayList arrayList) {
		// TODO Auto-generated constructor stub
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Date getCodeCreationDate() {
		return codeCreationDate;
	}

	public void setCodeCreationDate(Date codeCreationDate) {
		this.codeCreationDate = codeCreationDate;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	private String email;
    private String verificationCode;

    @Temporal(TemporalType.TIMESTAMP)
    private Date codeCreationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles")
    private List<Role> roles;
}
