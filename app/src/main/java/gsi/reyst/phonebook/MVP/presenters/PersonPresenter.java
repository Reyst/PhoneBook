package gsi.reyst.phonebook.MVP.presenters;


import android.os.Bundle;

import gsi.reyst.phonebook.MVP.views.PersonView;
import gsi.reyst.phonebook.domain.Person;
import gsi.reyst.phonebook.domain.Phone;

public interface PersonPresenter {

    void addPhone();

    Person getPerson();

    void init(PersonView view, Bundle params);

    void save();

    void editPhone(Phone phone, int index);
}
