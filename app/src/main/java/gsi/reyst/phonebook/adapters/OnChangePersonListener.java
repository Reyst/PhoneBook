package gsi.reyst.phonebook.adapters;

import gsi.reyst.phonebook.domain.Person;

public interface OnChangePersonListener {

    void onPersonChanged(Person person, int index);

    void onPersonAdded(Person person);

}
