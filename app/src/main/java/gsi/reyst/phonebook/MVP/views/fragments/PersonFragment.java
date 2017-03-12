package gsi.reyst.phonebook.MVP.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import gsi.reyst.phonebook.MVP.presenters.PersonPresenter;
import gsi.reyst.phonebook.MVP.presenters.impl.PersonPresenterImpl;
import gsi.reyst.phonebook.MVP.views.PersonView;
import gsi.reyst.phonebook.R;
import gsi.reyst.phonebook.RxViewEvents;
import gsi.reyst.phonebook.adapters.OnChangePersonListener;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class PersonFragment extends BaseFragment implements PersonView {

    private TextInputEditText edName;
    private TextInputEditText edSurname;

    private PersonPresenter presenter;
    private RecyclerView rvPhones;
    private Button btnSave;

    private OnChangePersonListener onChangePersonListener;

    private CompositeDisposable disposable;

    public static PersonFragment getInstance(Bundle params) {

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(params);

        return fragment;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new PersonPresenterImpl();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_person, container, false);

        edName = (TextInputEditText) view.findViewById(R.id.te_name);
        edSurname = (TextInputEditText) view.findViewById(R.id.te_surname);

        rvPhones = (RecyclerView) view.findViewById(R.id.rv_phones);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        rvPhones.setLayoutManager(llm);

        btnSave = (Button) view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.save();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle params = getArguments();
        if (params == null) {
            params = new Bundle();
        }

        presenter.init(this, params);

        edName.setText(presenter.getPerson().getName());
        edSurname.setText(presenter.getPerson().getSurname());
    }

    @Override
    public void onStart() {
        super.onStart();

        disposable = new CompositeDisposable();

        disposable.add(
                Observable.combineLatest(
                        RxViewEvents
                                .getTextChangeObservable(edName)
                                .doOnNext(new Consumer<String>() {
                                    @Override
                                    public void accept(String s) throws Exception {
                                        presenter.getPerson().setName(s);
                                    }
                                })
                                .map(new Function<String, Boolean>() {
                                    @Override
                                    public Boolean apply(String s) throws Exception {
                                        return !TextUtils.isEmpty(s);
                                    }
                                })
                                .doOnNext(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean value) throws Exception {
                                        checkAndSetError(value, edName, getString(R.string.str_cannot_be_empty));
                                    }
                                }),
                        RxViewEvents
                                .getTextChangeObservable(edSurname)
                                .doOnNext(new Consumer<String>() {
                                    @Override
                                    public void accept(String s) throws Exception {
                                        presenter.getPerson().setSurname(s);
                                    }
                                })
                                .map(new Function<String, Boolean>() {
                                    @Override
                                    public Boolean apply(String s) throws Exception {
                                        return !TextUtils.isEmpty(s);
                                    }
                                })
                                .doOnNext(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean value) throws Exception {
                                        checkAndSetError(value, edSurname, getString(R.string.str_cannot_be_empty));
                                    }
                                }),
                        new BiFunction<Boolean, Boolean, Boolean>() {
                            @Override
                            public Boolean apply(Boolean f1, Boolean f2) throws Exception {
                                return f1 && f2;
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean value) throws Exception {
                                btnSave.setEnabled(value);
                            }
                        }));
    }

    private void checkAndSetError(Boolean value, TextInputEditText field, String s) {
        if (!value) field.setError(s);
        else field.setError(null);
    }

    @Override
    public void onStop() {
        disposable.clear();
        super.onStop();
    }

    @Override
    public void fabClick() {
        presenter.addPhone();
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        rvPhones.setAdapter(adapter);
    }

    @Override
    public OnChangePersonListener getOnChangePersonListener() {
        return onChangePersonListener;
    }

    @Override
    public void goBack() {
        getFragmentChanger().back();
    }

    public void setOnChangePersonListener(OnChangePersonListener onChangePersonListener) {
        this.onChangePersonListener = onChangePersonListener;
    }
}
