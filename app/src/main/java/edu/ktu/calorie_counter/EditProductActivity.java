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

public class EditProductActivity extends AppCompatActivity {

    EditText title, calorie;
    String titlesend, caloriesend;
    private DatabaseReference mDatabase;
    private Listdata listdata;
    Button updates,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        updates=findViewById(R.id.updatesbutton);
        delete=findViewById(R.id.deletedbutton);
        final Intent i=getIntent();

        String gettitle=i.getStringExtra("title");
        String getcalorie=i.getStringExtra("calorie");
        final String id=i.getStringExtra("id");
        title=findViewById(R.id.title);
        calorie=findViewById(R.id.calorie);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        title.setText(gettitle);
        calorie.setText(getcalorie);
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
        caloriesend=calorie.getText().toString();
        Listdata listdata = new Listdata(id,titlesend, caloriesend);
        mDatabase.child("Product").child(id).setValue(listdata).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EditProductActivity.this, "Product Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ProductListActivity.class));
                    }
                });

    }

    private void deleteProduct(String id) {
        mDatabase.child("Product").child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditProductActivity.this,"Product Updated",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ProductListActivity.class));

                    }
                });
    }
}
