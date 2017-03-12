package gsi.reyst.phonebook.MVP.models;

import gsi.reyst.phonebook.domain.Person;
import gsi.reyst.phonebook.domain.Phone;

public interface PersonModel {
    void save();

    Person getPerson();

    boolean isPersonNew();

    int getIndex();

    void addPhone(Phone phone);
}
