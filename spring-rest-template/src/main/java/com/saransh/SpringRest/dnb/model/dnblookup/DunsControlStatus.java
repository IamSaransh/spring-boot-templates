package com.saransh.SpringRest.dnb.model.dnblookup;

import lombok.Data;

@Data
public class DunsControlStatus{
	private boolean isMailUndeliverable;
	private OperatingStatus operatingStatus;
}