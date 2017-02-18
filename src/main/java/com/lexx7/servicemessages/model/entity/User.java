package com.lexx7.servicemessages.model.entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_account")
public class User {
	
    public User() {
		super();
	}

	public User(Long id) {
		super();
		this.id = id;
	}

	@Id
    @GeneratedValue
    private Long id;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "edit_time")
    private Date editTime;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column
    private String email;

    @Column(unique = true)
    private String login;

    @Column
    private String password;

    @Column
    private boolean admin = false;

    @Column
    private boolean active = true;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
    	// FindBug: EI_EXPOSE_REP2
    	// java.util.Date is mutable object
		return new Date(createTime.getTime());
	}

	public void setCreateTime(Date createTime) {
    	// FindBug: EI_EXPOSE_REP2
    	// java.util.Date is mutable object
		this.createTime =  new Date(createTime.getTime());
	}

	public Date getEditTime() {
    	// FindBug: EI_EXPOSE_REP2
    	// java.util.Date is mutable object
		return new Date(editTime.getTime());
	}

	public void setEditTime(Date editTime) {
    	// FindBug: EI_EXPOSE_REP2
    	// java.util.Date is mutable object
		this.editTime = new Date(editTime.getTime());
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getFullName() {
		return String.format("%s %s %s", this.lastName, this.firstName, this.middleName);
	}
}
