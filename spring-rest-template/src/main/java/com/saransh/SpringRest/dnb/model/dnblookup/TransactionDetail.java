package com.saransh.SpringRest.dnb.model.dnblookup;

import lombok.Data;

@Data
public class TransactionDetail{
	private String serviceVersion;
	private String inLanguage;
	private String transactionID;
	private String transactionTimestamp;
}