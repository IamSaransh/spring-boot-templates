package com.saransh.SpringRest.dnb.model.dnblookup;

import java.util.List;
import lombok.Data;

@Data
public class MatchQualityInformation{
	private int confidenceCode;
	private int matchGradeComponentsCount;
	private List<MatchGradeComponentsItem> matchGradeComponents;
	private List<MatchDataProfileComponentsItem> matchDataProfileComponents;
	private double nameMatchScore;
	private String matchGrade;
	private String matchDataProfile;
	private int matchDataProfileComponentsCount;
}