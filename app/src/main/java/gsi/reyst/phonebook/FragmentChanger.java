package gsi.reyst.phonebook;

import android.os.Bundle;

import gsi.reyst.phonebook.MVP.views.fragments.BaseFragment;

public interface FragmentChanger {

    int FRAGMENT_PERSON_LIST = 1;
    int FRAGMENT_PERSON_EDIT = 2;

    String KEY_POSITION = "position";
    String KEY_PERSON = "person";

    BaseFragment getFragmentById(int fragmentId, Bundle params, boolean saveInstance);

    void changeFragment(BaseFragment newFragment, boolean saveState);

    void back();

}
