package com.challenge.parking.dto;

import java.io.Serializable;

public class ParkingBayKey implements Serializable {

	private static final long serialVersionUID = -6508336115629390465L;

	private Long bayId;

	private Integer index;

	public ParkingBayKey(Long bayId, Integer index) {

		this.bayId = bayId;
		this.index = index;
	}

	public Long getBayId() {

		return bayId;
	}

	public void setBayId(Long bayId) {

		this.bayId = bayId;
	}

	public Integer getIndex() {

		return index;
	}

	public void setIndex(Integer index) {

		this.index = index;
	}
}
