package com.akqa.kiev.conferer.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="conference_categories")
public class ConferenceCategory extends AbstractEntity {
	
	@Column(nullable=false, length=255)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
