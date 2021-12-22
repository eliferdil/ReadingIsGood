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
@Table(name = "CURRENTLY_PROCESSING_ITEM")
public class CurrentlyProcessingItem {

	@Id
	@Column(name="ID")
	private long id;
}
