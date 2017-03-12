package gsi.reyst.phonebook.MVP.presenters;

import gsi.reyst.phonebook.MVP.views.PersonListView;

public interface PersonListPresenter {

    void addPerson();

    void init(PersonListView view);
}
