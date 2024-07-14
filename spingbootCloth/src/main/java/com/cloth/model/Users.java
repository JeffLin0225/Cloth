package com.cloth.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="username" ,nullable=false , unique = true)
	private String user_name;
	
	@Column(name="user_pwd" ,nullable=false )
	private String user_pwd;
	
	@Column(name="email" ,nullable=false , unique = true)
	private String email;
	
	private String gender;
	
	@Lob
	private byte[]  img;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date birth;
	
	private String phone_num;
	
	private String address;
	
	private String user_type;
	
	private String status;
	
	private String created_by;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date created_at;
	
	private String updated_by;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@Temporal(TemporalType.TIMESTAMP) 
	private Date updated_at;
	
	 @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
	 private List<Cart> cart = new ArrayList<>();
	 @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
	 private List<Returns> returns = new ArrayList<>();
	 @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
	 private List<Couponowner> couponowner = new ArrayList<>();
	 @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
	 private List<Orders> orders = new ArrayList<>();
	 @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
	 private List<Customerservice> customer_service = new ArrayList<>();
	 @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
	 private List<Comment> comment = new ArrayList<>();
	 @PrePersist
		public void oncreate() {
			if (created_at == null) {
				created_at = new Date();
			}
		}

}
