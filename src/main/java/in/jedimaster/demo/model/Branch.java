package in.jedimaster.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nishant on 18/3/16.
 */
public class Branch {

    private long bankId;
    private String ifsc;
    private String branch;
    private String addr;
    private String city;
    private String district;
    private String state;
    private String bankName;

    private Branch() {}

    public static class Builder {

        private Branch ins = new Branch();

        public Builder ifsc(String ifsc) {
            ins.ifsc = ifsc;
            return this;
        }

        public Builder bankId(long bankId) {
            ins.bankId = bankId;
            return this;
        }

        public Builder branch(String branch) {
            ins.branch = branch;
            return this;
        }

        public Builder addr(String addr) {
            ins.addr = addr;
            return this;
        }

        public Builder city(String city) {
            ins.city = city;
            return this;
        }

        public Builder district(String district) {
            ins.district = district;
            return this;
        }

        public Builder state(String state) {
            ins.state = state;
            return this;
        }

        public Builder bankName(String bankName) {
            ins.bankName = bankName;
            return this;
        }

        public Branch build() {
            // validate();
            return ins;
        }
    }

    @JsonProperty
    public String getIfsc() {
        return ifsc;
    }

    @JsonProperty
    public long getBankId() {
        return bankId;
    }

    @JsonProperty
    public String getBranch() {
        return branch;
    }

    @JsonProperty
    public String getAddr() {
        return addr;
    }

    @JsonProperty
    public String getCity() {
        return city;
    }

    @JsonProperty
    public String getDistrict() {
        return district;
    }

    @JsonProperty
    public String getState() {
        return state;
    }

    @JsonProperty
    public String getBankName() {
        return bankName;
    }
}
