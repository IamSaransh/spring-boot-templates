package com.saransh.SpringRest.dnb.model.dnblimited;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DnBLimited {
    private List<MatchCandidatesItem> matchCandidates;
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MatchCandidatesItem implements  Comparable<MatchCandidatesItem> {
        private Organization organization;
        private MatchQualityInformation matchQualityInformation;

        /**
         * Compares this object with the specified object for order.  Returns a
         * negative integer, zero, or a positive integer as this object is less
         * than, equal to, or greater than the specified object.
         *
         * <p>The implementor must ensure {@link Integer#signum
         * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
         * all {@code x} and {@code y}.  (This implies that {@code
         * x.compareTo(y)} must throw an exception if and only if {@code
         * y.compareTo(x)} throws an exception.)
         *
         * <p>The implementor must also ensure that the relation is transitive:
         * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
         * {@code x.compareTo(z) > 0}.
         *
         * <p>Finally, the implementor must ensure that {@code
         * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
         * == signum(y.compareTo(z))}, for all {@code z}.
         *
         * @param o the object to be compared.
         * @return a negative integer, zero, or a positive integer as this object
         * is less than, equal to, or greater than the specified object.
         * @throws NullPointerException if the specified object is null
         * @throws ClassCastException   if the specified object's type prevents it
         *                              from being compared to this object.
         * @apiNote It is strongly recommended, but <i>not</i> strictly required that
         * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
         * class that implements the {@code Comparable} interface and violates
         * this condition should clearly indicate this fact.  The recommended
         * language is "Note: this class has a natural ordering that is
         * inconsistent with equals."
         */
        @Override
        public int compareTo(@NotNull MatchCandidatesItem o) {
            if(this.matchQualityInformation.getConfidenceCode()> o.matchQualityInformation.getConfidenceCode())
                return Integer.compare(this.matchQualityInformation.getConfidenceCode(), o.getMatchQualityInformation().getConfidenceCode());
            else{
                return this.matchQualityInformation.getMatchGrade().compareTo(o.getMatchQualityInformation().getMatchGrade());
            }
        }
    }
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Organization {
        private String duns;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MatchQualityInformation {
        private int confidenceCode;
        private int matchGradeComponentsCount;
        private String matchGrade;
    }
}






