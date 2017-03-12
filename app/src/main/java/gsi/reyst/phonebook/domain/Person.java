package gsi.reyst.phonebook.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Person implements Serializable {

    private String name;
    private String surname;

    private List<Phone> phones;

    public Person(String name, String surname, Collection<Phone> phones) {
        this();
        this.name = name;
        this.surname = surname;

        setPhones(phones);
    }

    public Person() {
        this.phones = new ArrayList<>();
    }

    public int size() {
        return phones.size();
    }

    public boolean contains(Phone phone) {
        return phones.contains(phone);
    }

    public void add(Phone phone) {
        phones.add(phone);
    }

    public void remove(Phone phone) {
        phones.remove(phone);
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public List<Phone> getPhones() {
        return this.phones;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhones(Collection<Phone> phones) {
        this.phones.clear();
        if (phones != null) {
            this.phones.addAll(phones);
        }
    }


}
