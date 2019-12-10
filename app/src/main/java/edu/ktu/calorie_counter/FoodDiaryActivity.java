package edu.ktu.calorie_counter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;

import edu.ktu.calorie_counter.Adapter.FoodDiaryAdapter;
import edu.ktu.calorie_counter.Adapter.FoodDiaryAdapter2;
import edu.ktu.calorie_counter.Model.Listdata3;

import static edu.ktu.calorie_counter.CalorieCalculatorActivity.parameter;

public class FoodDiaryActivity extends AppCompatActivity {

    TextView addProduct;
    EditText date;
    DatePickerDialog datePickerDialog;
    TextView get, spent, left, weightR, genderR;

    RecyclerView recyclerView;
    SearchView searchView;


    FirebaseAuth firebaseAuth;
    DatabaseReference ref;
    FirebaseDatabase firebaseDatabase;
    ArrayList<Listdata3> list;

    FirebaseUser user;
    ImageView share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_diary);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Food diary");
        
        get = findViewById(R.id.diary_calorie_get);
        spent = findViewById(R.id.diary_calorie_spend);
        /*left = findViewById(R.id.diary_calorie_need);*/
        /*date = findViewById(R.id.date);*/
        addProduct = findViewById(R.id.textViewHeadlineBreakfast);
        searchView = findViewById(R.id.search);
        weightR = findViewById(R.id.weightRRRR);
        genderR = findViewById(R.id.genderRRRRR);
        share = findViewById(R.id.share);


        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddFoodActivity.class));
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Food diary/" + id );

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
    }

    public void share(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        list.add(ds.getValue(Listdata3.class));
                    }
                    FoodDiaryAdapter2 adapterClass = new FoodDiaryAdapter2(list);
                    recyclerView.setAdapter(adapterClass);
                }
                user = firebaseAuth.getCurrentUser();

                Query query = firebaseDatabase.getReference("Users").orderByChild("email").equalTo(user.getEmail());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            String weight = ""+ ds.child("weight").getValue();


                            double totalamount2 = 0.0;
                            String gender = genderR.getText().toString().toLowerCase();

                            if (gender.equals("female")){
                                parameter = 0.9;
                                int Weight = Integer.parseInt(weight);
                                totalamount2 = Weight * parameter * 24;
                            }
                            if (gender.equals("male")){
                                parameter = 1.0;
                                int Weight = Integer.parseInt(weight);
                                totalamount2 = Weight * parameter * 24;
                            }
                            BigDecimal bd = new BigDecimal(totalamount2).setScale(2, RoundingMode.HALF_EVEN);
                            totalamount2 = bd.doubleValue();
                            String total = String.valueOf(totalamount2);

                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi! My weight is: " + weight + " kg and my daily calorie: " + total);
                            sendIntent.setType("text/plain");

                            Intent shareIntent = Intent.createChooser(sendIntent, null);
                            startActivity(shareIntent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FoodDiaryActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            list.add(ds.getValue(Listdata3.class));
                        }
                        FoodDiaryAdapter2 adapterClass = new FoodDiaryAdapter2(list);
                        recyclerView.setAdapter(adapterClass);
                    }

                    user = firebaseAuth.getCurrentUser();

                    Query query = firebaseDatabase.getReference("Users").orderByChild("email").equalTo(user.getEmail());
                    query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            String weight = ""+ ds.child("weight").getValue();
                            weightR.setText(weight);

                            String targetweight = ""+ ds.child("gender").getValue();
                            genderR.setText(targetweight);

                            double totalamount2 = 0.0;
                            String gender = genderR.getText().toString().toLowerCase();

                            if (gender.equals("female")){
                                parameter = 0.9;
                                //String weight = weightR.getText().toString();
                                int Weight = Integer.parseInt(weight);
                                totalamount2 = Weight * parameter * 24;
                            }
                            if (gender.equals("male")){
                                parameter = 1.0;
                                //String weight = weightR.getText().toString();
                                int Weight = Integer.parseInt(weight);
                                totalamount2 = Weight * parameter * 24;
                            }
                            BigDecimal bd = new BigDecimal(totalamount2).setScale(2, RoundingMode.HALF_EVEN);
                            totalamount2 = bd.doubleValue();
                            String total = String.valueOf(totalamount2);
                            get.setText(total);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                    });

                    int totalamount = 0;

                    for (DataSnapshot snap : dataSnapshot.getChildren()){
                        Listdata3 data = snap.getValue(Listdata3.class);
                        int cal = Integer.parseInt(data.getCalorie());
                        int count = Integer.parseInt(data.getCount());
                        totalamount = totalamount + ( cal * count);
                        String sttotal=String.valueOf(totalamount);
                        spent.setText(sttotal);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(FoodDiaryActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filter(newText);
                    return true;
                }
            });
        }
    }

    private void filter(String str){

        ArrayList<Listdata3> myList = new ArrayList<>();
        for (Listdata3 object : list){
            if (object.getDate().startsWith(str)){
                myList.add(object);
            }
            FoodDiaryAdapter2 adapterClass = new FoodDiaryAdapter2(myList);
            recyclerView.setAdapter(adapterClass);
        }
    }

    /*Atsijungimas*/
    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
        } else {
            startActivity(new Intent(FoodDiaryActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            goHome();
            return true;
        }
        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goHome() {
        startActivity(new Intent(FoodDiaryActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home2, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
