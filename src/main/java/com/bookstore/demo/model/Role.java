package com.bookstore.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="ROLE")
public class Role extends BaseModel implements GrantedAuthority {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="roleName")
	private String roleName;

	@Override
	public String getAuthority() {
		return roleName;
	}
}
