package pooja.firebaserealtimedbexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

import static pooja.firebaserealtimedbexample.Activity_UI.databaseReference;
import static pooja.firebaserealtimedbexample.Activity_UI.uid;


public class ViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    //Variable for the update and delete operation
    String u_title, u_note, post_key;


    //Update Field.....
    EditText editTextTitleUpdate, editTextNotesUpdate;
    Button buttonUpdate, buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Testing").child(uid);
        databaseReference.keepSynced(true);
        // Recycler View
        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View myview;

        public MyViewHolder(View itemView) {
            super(itemView);
            myview = itemView;
        }

        public void setTitle(String title) {
            TextView title_textView = myview.findViewById(R.id.rtitle);
            title_textView.setText(title);
        }

        public void setNote(String note) {
            TextView note_textView = myview.findViewById(R.id.note);
            note_textView.setText(note);
        }

        public void setdate(String date) {
            TextView date_textView = myview.findViewById(R.id.date);
            date_textView.setText(date);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(Data.class, R.layout.item_data, MyViewHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(MyViewHolder viewHolder, final Data model, final int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setNote(model.getNote());
                viewHolder.setdate(model.getDate());
                viewHolder.myview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //for getting the key at  position of the task
                        post_key = getRef(position).getKey();
                        u_title = model.getTitle();
                        u_note = model.getNote();
                        updatedata();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void updatedata() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup uviewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View udialogView = LayoutInflater.from(ViewActivity.this).inflate(R.layout.update_input_field, uviewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewActivity.this);


        //setting the view of the builder to our custom view that we already inflated
        builder.setView(udialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog ualertDialog = builder.create();

        //Update
        editTextTitleUpdate = udialogView.findViewById(R.id.edit_Title_update);
        editTextNotesUpdate = udialogView.findViewById(R.id.edit_Notes_update);
        editTextTitleUpdate.setText(u_title);
        editTextTitleUpdate.setSelection(u_title.length());

        editTextNotesUpdate.setText(u_note);
        editTextNotesUpdate.setSelection(u_title.length());

        buttonDelete = udialogView.findViewById(R.id.btn_del);
        buttonUpdate = udialogView.findViewById(R.id.btn_Update);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                u_title = editTextTitleUpdate.getText().toString().trim();
                u_note = editTextNotesUpdate.getText().toString().trim();

                String uDate = DateFormat.getDateInstance().format(new Date());
                Data udata = new Data(u_title, u_note, uDate, post_key);
                databaseReference.child(post_key).setValue(udata);
                ualertDialog.dismiss();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(post_key).removeValue();
                ualertDialog.dismiss();
            }
        });

        ualertDialog.show();
    }

}
