package com.lexx7.servicemessages.model.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SimpleUserDetails implements UserDetails {
	private static final long serialVersionUID = -4779794457989413429L;
	
	private String username;
	
	private String password;
	
	private boolean enabled;

	private boolean admin;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>(); 
		grantedAuthority.add(new SimpleGrantedAuthority(UserRole.ROLE_USER.name()));
		if (isAdmin()) {
			grantedAuthority.add(new SimpleGrantedAuthority(UserRole.ROLE_ADMIN.name()));
		}
		return grantedAuthority;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
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


}
