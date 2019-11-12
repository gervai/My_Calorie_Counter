package edu.ktu.calorie_counter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.ktu.calorie_counter.Model.Listdata;

public class EditActivityActivity extends AppCompatActivity {

    EditText title,desc;
    String titlesend,descsend;
    private DatabaseReference mDatabase;
    private Listdata listdata;
    Button updates,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_activity);

        updates=findViewById(R.id.updatesbutton);
        delete=findViewById(R.id.deletedbutton);
        final Intent i=getIntent();

        String gettitle=i.getStringExtra("title");
        String getdesc=i.getStringExtra("desc");
        final String id=i.getStringExtra("id");
        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        title.setText(gettitle);
        desc.setText(getdesc);
        updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
                UpdateProduct(id);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
                deleteProduct(id);
            }
        });
    }

    private void UpdateProduct(String id)
    {
        titlesend=title.getText().toString();
        descsend=desc.getText().toString();
        Listdata listdata = new Listdata(id,titlesend, descsend);
        mDatabase.child("Activities").child(id).setValue(listdata).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EditActivityActivity.this, "Activity Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ActivityListActivity.class));
                    }
                });

    }

    private void deleteProduct(String id) {
        mDatabase.child("Activities").child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditActivityActivity.this,"Activity Updated",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ActivityListActivity.class));

                    }
                });
    }
}
