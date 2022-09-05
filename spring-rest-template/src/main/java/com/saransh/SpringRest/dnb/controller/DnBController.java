package com.saransh.SpringRest.dnb.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.saransh.SpringRest.dnb.model.dnblimited.DnBLimited;
import com.saransh.SpringRest.dnb.model.dnblookup.DnBLookup;
import com.saransh.SpringRest.dnb.model.dnblookup.MatchCandidatesItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/dnb")
public class DnBController {

    @PostMapping("/post")
    public ResponseEntity<List<MatchCandidatesItem>> returnBestFromInput(@RequestBody DnBLookup body){
        List<MatchCandidatesItem> matchCandidates = body.getMatchCandidates();
        Collections.sort(matchCandidates, Collections.reverseOrder());
        matchCandidates.forEach(x -> System.out.println(x.getMatchQualityInformation().getConfidenceCode()
        + " \n" + x.getMatchQualityInformation().getMatchGrade()));
        return ResponseEntity.ok(matchCandidates);
    }

    @PostMapping("/post2")
    public ResponseEntity<Object> returnBestFromInput(@RequestBody DnBLimited  body) throws IOException {
        System.out.println(body);
        List<DnBLimited.MatchCandidatesItem> matchCandidates = body.getMatchCandidates();
        matchCandidates.sort(Collections.reverseOrder());
        return ResponseEntity.ok(matchCandidates);
    }
}

