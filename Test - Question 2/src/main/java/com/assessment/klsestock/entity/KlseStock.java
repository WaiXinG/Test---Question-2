package com.assessment.klsestock.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="klse_stock")
public class KlseStock {
	
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@GeneratedValue(generator = "system-uuid")
	private String id;
	
	@Column(name = "record_datetime")
	private Date recordDateTime;
	
	@Column(name = "num_gainers")
	private int numGainers;
	
	@Column(name = "num_losers")
	private int numLoser;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getRecordDateTime() {
		return recordDateTime;
	}

	public void setRecordDateTime(Date recordDateTime) {
		this.recordDateTime = recordDateTime;
	}

	public int getNumGainers() {
		return numGainers;
	}

	public void setNumGainers(int numGainers) {
		this.numGainers = numGainers;
	}

	public int getNumLoser() {
		return numLoser;
	}

	public void setNumLoser(int numLoser) {
		this.numLoser = numLoser;
	}
}
