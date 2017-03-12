package gsi.reyst.phonebook.adapters;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import gsi.reyst.phonebook.R;
import gsi.reyst.phonebook.domain.Person;


public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> implements OnChangePersonListener {

    private PersonEditor personEditor;

    private List<Person> model;

    public PersonAdapter(List<Person> data, PersonEditor editor) {
        personEditor = editor;
        this.model = data;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        Person person = model.get(position);
        holder.text.setText(person.getName() + ", " + person.getSurname());
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onPersonChanged(Person person, int index) {
        model.set(index, person);
        notifyItemChanged(index);
    }

    @Override
    public void onPersonAdded(Person person) {
        model.add(person);
        notifyDataSetChanged();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {

        TextView text;

        PersonViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tv_text);
            ImageView iMenu = (ImageView) itemView.findViewById(R.id.btn_menu);
            iMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(v.getContext(), v);
                    popup.setOnMenuItemClickListener(PersonViewHolder.this);
                    popup.inflate(R.menu.actions);
                    popup.show();
                }
            });
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            final int pos = getAdapterPosition();

            switch (item.getItemId()) {
                case R.id.menu_edit:
                    personEditor.editPerson(model.get(pos), pos);
                    break;
                case R.id.menu_del:
                    model.remove(pos);
                    notifyItemRemoved(pos);
                    break;
            }
            return true;
        }
    }

    public interface PersonEditor {
        void editPerson(Person person, int index);
    }


}
