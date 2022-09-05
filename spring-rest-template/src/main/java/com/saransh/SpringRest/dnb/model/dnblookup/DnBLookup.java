package com.saransh.SpringRest.dnb.model.dnblookup;

import java.util.List;
import lombok.Data;

@Data
public class DnBLookup {
	private CleanseAndStandardizeInformation cleanseAndStandardizeInformation;
	private InquiryDetail inquiryDetail;
	private List<MatchCandidatesItem> matchCandidates;
	private int candidatesMatchedQuantity;
	private String matchDataCriteria;
	private TransactionDetail transactionDetail;
}