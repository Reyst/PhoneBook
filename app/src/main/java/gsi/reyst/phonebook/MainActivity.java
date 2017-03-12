package gsi.reyst.phonebook;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;

import gsi.reyst.phonebook.MVP.views.fragments.BaseFragment;
import gsi.reyst.phonebook.MVP.views.fragments.PersonFragment;
import gsi.reyst.phonebook.MVP.views.fragments.PersonListFragment;

public class MainActivity extends AppCompatActivity implements FragmentChanger {

    private SparseArray<BaseFragment> fragmentHolder;

    private BaseFragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentFragment != null) {
                    currentFragment.fabClick();
                }
            }
        });

        fragmentHolder = new SparseArray<>();

        changeFragment(getFragmentById(FRAGMENT_PERSON_LIST, null, true), false);

    }

    @Override
    public BaseFragment getFragmentById(int fragmentId, Bundle params, boolean saveInstance) {
        BaseFragment result = fragmentHolder.get(fragmentId);
        if (result == null) {
            switch (fragmentId) {
                case FRAGMENT_PERSON_LIST:
                    result = PersonListFragment.getInstance(params);
                    break;
                case FRAGMENT_PERSON_EDIT:
                    result = PersonFragment.getInstance(params);
                    break;
            }

            if (result != null) {
                result.setFragmentChanger(this);
                if (saveInstance) {
                    fragmentHolder.put(fragmentId, result);
                }
            }
        }
        return result;
    }

    @Override
    public void changeFragment(BaseFragment newFragment, boolean saveState) {
        if (newFragment != null && currentFragment != newFragment) {
            FragmentTransaction tx = getFragmentManager().beginTransaction();
            if (currentFragment != null && saveState) {
                tx.addToBackStack(currentFragment.getClass().getName());
            }
            tx.replace(R.id.fragment_container, newFragment).commit();
            currentFragment = newFragment;
        }
    }

    @Override
    public void back() {
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currentFragment = (BaseFragment) getFragmentManager().findFragmentById(R.id.fragment_container);
    }
}
