package com.saransh.SpringRest.dnb.model.dnblookup;

import lombok.Data;

@Data
public class Address{
	private String countryISOAlpha2Code;
	private String postalCode;
	private String addressLocality;
	private String addressRegion;
	private StreetAddressLine streetAddressLine;
}