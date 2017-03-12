package gsi.reyst.phonebook.domain;

import java.io.Serializable;

public class Phone implements Serializable {

    private String phoneNo;

    public Phone(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Phone() {
        phoneNo = "";
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
