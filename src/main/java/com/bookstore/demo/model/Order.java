package com.bookstore.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ORD3R")
public class Order extends BaseModel {

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@Column(name = "TOTAL_PRICE")
	private double totalPrice;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	private List<OrderBook> orderBooks;

}
