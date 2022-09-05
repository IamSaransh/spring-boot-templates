package com.saransh.SpringRest.dnb.model.dnblookup;

import lombok.Data;

@Data
public class MailingAddress{
	private AddressCountry addressCountry;
	private Object postalCodeExtension;
	private StreetAddress streetAddress;
	private Object postalCode;
	private AddressLocality addressLocality;
	private AddressRegion addressRegion;
}