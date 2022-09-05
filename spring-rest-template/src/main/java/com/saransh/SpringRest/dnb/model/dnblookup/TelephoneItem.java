package com.saransh.SpringRest.dnb.model.dnblookup;

import lombok.Data;

@Data
public class TelephoneItem{
	private String telephoneNumber;
	private boolean isUnreachable;
}