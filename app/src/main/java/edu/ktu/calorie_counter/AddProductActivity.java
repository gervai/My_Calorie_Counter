package edu.ktu.calorie_counter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.ktu.calorie_counter.Model.Listdata;

public class AddProductActivity extends AppCompatActivity {

    EditText title,calorie;
    String titlesend,caloriesend;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        title=findViewById(R.id.title);
        calorie=findViewById(R.id.calorie);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void AddProduct(View view) {
        titlesend=title.getText().toString();
        caloriesend=calorie.getText().toString();
        if(TextUtils.isEmpty(titlesend) || TextUtils.isEmpty(caloriesend)){
            return;
        }
        AddProduct(titlesend,caloriesend);

    }

    private void AddProduct(String titlesend, String caloriesend)
    {
        String id=mDatabase.push().getKey();
        Listdata listdata = new Listdata(id,titlesend, caloriesend);
        mDatabase.child("Product").child(id).setValue(listdata).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddProductActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ProductListActivity.class));
                    }
                });

    }
}
