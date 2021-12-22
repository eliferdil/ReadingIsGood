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
@Table(name = "BOOK")
public class Book extends BaseModel {
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "STOCK", nullable = false)
	private int stock;

	@Column(name = "PRICE", nullable = false)
	private double price;

	@OneToMany(mappedBy = "book")
	private List<OrderBook> orderBooks;
}
