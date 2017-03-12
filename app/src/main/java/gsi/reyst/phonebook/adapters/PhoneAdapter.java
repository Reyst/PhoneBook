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
import gsi.reyst.phonebook.domain.Phone;


public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneHolder> { // implements OnChangePhoneListener {

    private List<Phone> model;

    private PhoneEditor phoneEditor;

    public PhoneAdapter(List<Phone> data, PhoneEditor editor) {

        phoneEditor = editor;
        model = data;
    }

    @Override
    public PhoneHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new PhoneHolder(v);
    }

    @Override
    public void onBindViewHolder(PhoneHolder holder, int position) {
        holder.text.setText(model.get(position).getPhoneNo());
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

//    @Override
//    public void onPhoneChanged(Phone phone, int index) {
//        model.set(index, phone);
//        notifyItemChanged(index);
//    }

    class PhoneHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {

        TextView text;

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            final int pos = getAdapterPosition();

            switch (item.getItemId()) {
                case R.id.menu_edit:
                    phoneEditor.editPhone(model.get(pos), pos);
                    break;
                case R.id.menu_del:
                    model.remove(pos);
                    notifyItemRemoved(pos);
                    break;
            }
            return true;
        }


        PhoneHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tv_text);

            ImageView iMenu = (ImageView) itemView.findViewById(R.id.btn_menu);
            iMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(v.getContext(), v);
                    popup.setOnMenuItemClickListener(PhoneHolder.this);
                    popup.inflate(R.menu.actions);
                    popup.show();
                }
            });
        }
    }

    public interface PhoneEditor {
        void editPhone(Phone phone, int index);
    }
}
