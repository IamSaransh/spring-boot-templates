package com.saransh.SpringRest.dnb.model.dnblookup;

import java.util.List;
import lombok.Data;

@Data
public class Organization{
	private DunsControlStatus dunsControlStatus;
	private List<Object> tradeStyleNames;
	private CorporateLinkage corporateLinkage;
	private MailingAddress mailingAddress;
	private List<MostSeniorPrincipalsItem> mostSeniorPrincipals;
	private boolean isStandalone;
	private String duns;
	private List<Object> websiteAddress;
	private String primaryName;
	private List<TelephoneItem> telephone;
	private List<Object> registrationNumbers;
	private PrimaryAddress primaryAddress;
}