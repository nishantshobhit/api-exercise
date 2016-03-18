package in.jedimaster.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by nishant on 18/3/16.
 */
public class Branches {

    private List<Branch> branches;

    public Branches(List<Branch> branches) {
        this.branches = branches;
    }

    @JsonProperty
    public List<Branch> getBranches() {
        return this.branches;
    }
}
