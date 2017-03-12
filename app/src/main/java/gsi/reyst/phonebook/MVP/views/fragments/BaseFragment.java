package gsi.reyst.phonebook.MVP.views.fragments;

import android.app.Fragment;

import gsi.reyst.phonebook.FragmentChanger;
import gsi.reyst.phonebook.MVP.views.BaseView;

public abstract class BaseFragment extends Fragment implements BaseView {

    private FragmentChanger fragmentChanger;

    public FragmentChanger getFragmentChanger() {
        return fragmentChanger;
    }

    public void setFragmentChanger(FragmentChanger fragmentChanger) {
        this.fragmentChanger = fragmentChanger;
    }
}
