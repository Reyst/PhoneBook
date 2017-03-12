package gsi.reyst.phonebook.MVP.presenters.impl;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import gsi.reyst.phonebook.FragmentChanger;
import gsi.reyst.phonebook.MVP.presenters.PersonListPresenter;
import gsi.reyst.phonebook.MVP.views.PersonListView;
import gsi.reyst.phonebook.MVP.views.fragments.BaseFragment;
import gsi.reyst.phonebook.MVP.views.fragments.PersonFragment;
import gsi.reyst.phonebook.adapters.PersonAdapter;
import gsi.reyst.phonebook.domain.Person;

import static gsi.reyst.phonebook.FragmentChanger.FRAGMENT_PERSON_EDIT;
import static gsi.reyst.phonebook.FragmentChanger.KEY_PERSON;
import static gsi.reyst.phonebook.FragmentChanger.KEY_POSITION;

public class PersonListPresenterImpl implements PersonListPresenter, PersonAdapter.PersonEditor {

    private List<Person> model;

    private PersonListView view;

    private PersonAdapter adapter;

    @Override
    public void init(PersonListView view) {
        this.view = view;

        model = new ArrayList<>();

        adapter = new PersonAdapter(model, this);

        view.setAdapter(adapter);

    }

    @Override
    public void addPerson() {
        startEditPerson(new Person(), -1);
    }

    private void startEditPerson(Person person, int index) {

        Bundle params = new Bundle();

        params.putSerializable(KEY_PERSON, person);
        params.putSerializable(KEY_POSITION, index);

        BaseFragment epf = view.getFragmentChanger().getFragmentById(FRAGMENT_PERSON_EDIT, params, false);
        ((PersonFragment)epf).setOnChangePersonListener(adapter);

        view.getFragmentChanger().changeFragment(epf, true);

    }

    @Override
    public void editPerson(Person person, int index) {
        startEditPerson(person, index);
    }
}
