package edu.ktu.calorie_counter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import edu.ktu.calorie_counter.Model.Listdata2;

public class EditFoodActivity extends AppCompatActivity {

    TextView title,calorie;
    EditText count, dateET;
    String titlesend, caloriesend, countsend, datesend;
    private DatabaseReference mDatabase;
    private Listdata2 listdata;
    Button add;
    private FirebaseAuth firebaseAuth;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        add=findViewById(R.id.updatesbutton);
        final Intent i=getIntent();

        String gettitle=i.getStringExtra("title");
        String getcalorie=i.getStringExtra("calorie");
        String getcount=i.getStringExtra("count");
        String getdate=i.getStringExtra("date");
        final String id=i.getStringExtra("id");
        title=findViewById(R.id.title);
        calorie=findViewById(R.id.calorie);
        count=findViewById(R.id.count);
        dateET=findViewById(R.id.date);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Food diary");
        title.setText(gettitle);
        calorie.setText(getcalorie);
        count.setText(getcount);
        dateET.setText(getdate);

        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(EditFoodActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateET.setText(day + "-" + (month+1) + "-" + year);
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProduct(id);
            }
        });
    }

    private void AddProduct(String id)
    {
        titlesend=title.getText().toString();
        caloriesend =calorie.getText().toString();
        countsend=count.getText().toString();
        datesend=dateET.getText().toString();
        String id2=mDatabase.push().getKey();
        String id3=FirebaseAuth.getInstance().getCurrentUser().getUid();
        Listdata2 listdata = new Listdata2(id,titlesend, caloriesend, countsend, datesend);
        mDatabase.child(id3).child(id2).setValue(listdata).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EditFoodActivity.this, "Product Add to Food Diary list", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),FoodDiaryActivity.class));
                        finish();
                    }
                });

    }
}
