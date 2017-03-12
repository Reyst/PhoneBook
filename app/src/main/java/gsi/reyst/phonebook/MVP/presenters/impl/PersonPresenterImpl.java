package gsi.reyst.phonebook.MVP.presenters.impl;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;

import gsi.reyst.phonebook.MVP.models.PersonModel;
import gsi.reyst.phonebook.MVP.models.impl.PersonModelImpl;
import gsi.reyst.phonebook.MVP.presenters.PersonPresenter;
import gsi.reyst.phonebook.MVP.views.PersonView;
import gsi.reyst.phonebook.R;
import gsi.reyst.phonebook.adapters.OnChangePersonListener;
import gsi.reyst.phonebook.adapters.PhoneAdapter;
import gsi.reyst.phonebook.domain.Person;
import gsi.reyst.phonebook.domain.Phone;

import static gsi.reyst.phonebook.FragmentChanger.KEY_PERSON;
import static gsi.reyst.phonebook.FragmentChanger.KEY_POSITION;


public class PersonPresenterImpl implements PersonPresenter, PhoneAdapter.PhoneEditor {

    private PersonView view;
    private PersonModel model;
    private PhoneAdapter adapter;
    private Context context;


    @Override
    public void addPhone() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        final EditText input = new EditText(context);
        input.setId(R.id.phone_edit_text_id);
        //input.setInputType(InputType.TYPE_CLASS_PHONE);
        alert.setView(input);

        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String phoneNo = input.getText().toString();
                if (!TextUtils.isEmpty(phoneNo)) {
                    model.addPhone(new Phone(phoneNo));
                    adapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
    }

    @Override
    public Person getPerson() {
        return model.getPerson();
    }

    @Override
    public void init(PersonView view, Bundle params) {
        this.view = view;

        context = view.getContext();

        int pos = params.getInt(KEY_POSITION, -1);
        Person person = (Person) params.getSerializable(KEY_PERSON);
        if (person == null) person = new Person();

        model = new PersonModelImpl(person, pos);

        adapter = new PhoneAdapter(getPerson().getPhones(), this);
        view.setAdapter(adapter);

    }

    @Override
    public void save() {
        model.save();

        OnChangePersonListener listener = view.getOnChangePersonListener();
        if (listener != null) {
            if (model.isPersonNew()) listener.onPersonAdded(model.getPerson());
            else listener.onPersonChanged(model.getPerson(), model.getIndex());
        }

        view.goBack();

    }

    @Override
    public void editPhone(final Phone phone, final int index) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        final EditText input = new EditText(context);
        //input.setInputType(InputType.TYPE_CLASS_PHONE);
        input.setId(R.id.phone_edit_text_id);
        input.setText(phone.getPhoneNo());
        alert.setView(input);

        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String phoneNo = input.getText().toString();
                if (!TextUtils.isEmpty(phoneNo)) {
                    phone.setPhoneNo(phoneNo);
                    adapter.notifyItemChanged(index);
                }
                dialog.dismiss();
            }
        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
    }
}
