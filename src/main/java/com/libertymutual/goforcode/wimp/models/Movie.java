package com.libertymutual.goforcode.wimp.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(length=300, nullable=false)
	private String title;
	
	private Date date;
	
	private Long budget;

	@Column(length=500, nullable=false)
	private String distributor;
	
	
}
