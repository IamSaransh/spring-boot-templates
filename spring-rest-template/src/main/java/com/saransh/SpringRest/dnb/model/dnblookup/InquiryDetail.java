package com.saransh.SpringRest.dnb.model.dnblookup;

import lombok.Data;

@Data
public class InquiryDetail{
	private Address address;
	private int candidateMaximumQuantity;
	private String name;
}