package com.saransh.SpringRest.dnb.model.dnblookup;

import lombok.Data;

@Data
public class RegistrationNumbersItem{
	private String registrationNumber;
	private int typeDnBCode;
	private String typeDescription;
}