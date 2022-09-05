package com.saransh.SpringRest.dnb.model.dnblookup;

import lombok.Data;

@Data
public class PrimaryAddress{
	private AddressCountry addressCountry;
	private String postalCodeExtension;
	private StreetAddress streetAddress;
	private String postalCode;
	private AddressLocality addressLocality;
	private AddressRegion addressRegion;
}