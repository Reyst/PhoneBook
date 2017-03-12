package gsi.reyst.phonebook.MVP.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import gsi.reyst.phonebook.adapters.OnChangePersonListener;

public interface PersonView extends BaseView {

    void setAdapter(RecyclerView.Adapter adapter);

    OnChangePersonListener getOnChangePersonListener();

    Context getContext();

    void goBack();
}
