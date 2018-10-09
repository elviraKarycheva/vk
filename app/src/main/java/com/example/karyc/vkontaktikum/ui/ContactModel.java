package com.example.karyc.vkontaktikum.ui;

import java.util.ArrayList;

public class ContactModel {
    private String contactId, contactName, contactEmail,
            contactPhoto, contactOtherDetails;
    private ArrayList<String> contactNumber;

    public ContactModel(String contactId, String contactName,
                        ArrayList<String> contactNumber, String contactEmail, String contactPhoto,
                        String contactOtherDetails) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactNumber = contactNumber;
        this.contactPhoto = contactPhoto;
        this.contactOtherDetails = contactOtherDetails;
    }

    public String getContactID() {
        return contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public ArrayList<String> getContactNumber() {
        return contactNumber;
    }

    public String getContactPhoto() {
        return contactPhoto;
    }

    public String getContactOtherDetails() {
        return contactOtherDetails;
    }

    @Override
    public String toString() {
        return getContactName();
    }


}
