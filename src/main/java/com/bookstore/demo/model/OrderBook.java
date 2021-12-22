package com.bookstore.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ORDER_BOOK")
public class OrderBook extends BaseModel {

	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	private Order order;

	@OneToOne
	@JoinColumn(name = "BOOK_ID")
	private Book book;

	@Column(name = "quantity")
	private int quantity;

}
