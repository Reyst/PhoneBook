package gsi.reyst.phonebook.MVP.models.impl;

import gsi.reyst.phonebook.MVP.models.PersonModel;
import gsi.reyst.phonebook.domain.Person;
import gsi.reyst.phonebook.domain.Phone;

public class PersonModelImpl implements PersonModel {

    private Person currentPerson;
    private Person workPerson;

    private int index;


    public PersonModelImpl(Person person, int pos) {

        index = pos;

        currentPerson = person;

        workPerson = new Person(person.getName(), person.getSurname(), person.getPhones());

    }

    @Override
    public void save() {
        currentPerson.setName(workPerson.getName());
        currentPerson.setSurname(workPerson.getSurname());
        currentPerson.setPhones(workPerson.getPhones());
    }

    @Override
    public Person getPerson() {
        return workPerson;
    }

    @Override
    public boolean isPersonNew() {
        return index < 0;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void addPhone(Phone phone) {
        workPerson.getPhones().add(phone);
    }
}
