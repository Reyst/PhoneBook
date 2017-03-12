package gsi.reyst.phonebook.MVP.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gsi.reyst.phonebook.MVP.presenters.PersonListPresenter;
import gsi.reyst.phonebook.MVP.presenters.impl.PersonListPresenterImpl;
import gsi.reyst.phonebook.MVP.views.PersonListView;
import gsi.reyst.phonebook.R;

public class PersonListFragment extends BaseFragment implements PersonListView {

    private PersonListPresenter presenter;
    private RecyclerView rvPersons;
    private View fragmentView;

    public static PersonListFragment getInstance(Bundle params) {
        PersonListFragment fragment = new PersonListFragment();
        fragment.setArguments(params);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new PersonListPresenterImpl();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_person_list, container, false);
            rvPersons = (RecyclerView) fragmentView.findViewById(R.id.rv_person);
            LinearLayoutManager llm = new LinearLayoutManager(fragmentView.getContext(), LinearLayoutManager.VERTICAL, false);
            rvPersons.setLayoutManager(llm);

            presenter.init(this);
        } else {
            fragmentView.invalidate();
        }

        return fragmentView;
    }

    @Override
    public void fabClick() {
        presenter.addPerson();
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        rvPersons.setAdapter(adapter);
    }
}
