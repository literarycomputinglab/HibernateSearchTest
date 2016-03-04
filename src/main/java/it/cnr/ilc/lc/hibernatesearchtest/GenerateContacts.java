/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.lc.hibernatesearchtest;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author angelo
 */
public class GenerateContacts {

    public static final GenerateContacts INSTANCE = new GenerateContacts();

    private List<Contact> contacts;

    private GenerateContacts() {
        contacts = new ArrayList<>();
    }

    public List<Contact> generate(String... names) {
        for (String name : names) {
            System.err.println("GENERATE:" + name);
            contacts.add(new Contact(Double.valueOf(Math.random() * 10).intValue(), name, String.format("%s@pmail.com", name)));

        }
        return contacts;
    }

}
