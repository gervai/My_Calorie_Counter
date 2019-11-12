package edu.ktu.calorie_counter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CalorieCalculatorActivity extends AppCompatActivity {

    EditText tartgetWeight_et;
    EditText currentWeight_et;
    TextView calorieTarget;
    TextView calorieCurrent;
    Button calculateDayCalories;
    static double parameter;
    int currentWeight;
    int targetWeight;
    int currentCalories;
    int targetCalories;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_calculator);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Calorie Calculator");

        firebaseAuth = FirebaseAuth.getInstance();

        tartgetWeight_et = findViewById(R.id.targetWeight);
        currentWeight_et = findViewById(R.id.currentWeight);
        calorieTarget = findViewById(R.id.calorieTarget);
        calorieCurrent = findViewById(R.id.calorieCurrent);
        calculateDayCalories = findViewById(R.id.calculateDayCal);
    }

    //man : kg * 1 * 24
    //woman : kg * 0.9 * 24



    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        Button button = findViewById(R.id.calculateDayCal);


        // Check which radio button was clicked
        switch(view.getId()) {

            case R.id.radio_female:
                if (checked)
                    parameter = 0.9;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentWeight = Integer.valueOf(String.valueOf(currentWeight_et.getText()));
                        currentCalories = (int)(currentWeight* parameter * 24);
                        calorieCurrent.setText(String.valueOf((int) currentCalories));

                        targetWeight = Integer.valueOf(String.valueOf(tartgetWeight_et.getText()));
                        targetCalories =  (int)(targetWeight* parameter * 24);
                        calorieTarget.setText(String.valueOf((targetCalories)));
                    }
                });
                break;
            case R.id.radio_male:
                if (checked)
                    parameter = 1.0;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentWeight = Integer.valueOf(String.valueOf(currentWeight_et.getText()));
                        currentCalories = (int)(currentWeight* parameter * 24);
                        calorieCurrent.setText(String.valueOf((int) currentCalories));

                        targetWeight = Integer.valueOf(String.valueOf(tartgetWeight_et.getText()));
                        targetCalories =  (int)(targetWeight* parameter * 24);
                        calorieTarget.setText(String.valueOf((targetCalories)));

                    }
                });
                break;

        }
    }

    /*Atsijungimas*/
    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user !=null){
        }
        else {
            startActivity(new Intent(CalorieCalculatorActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }
}
