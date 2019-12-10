package edu.ktu.calorie_counter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    CardView cardProfile,cardCalculator, cardProductList, cardGoogleFit, cardFoodDiary, cardShare;

    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        cardProfile = findViewById(R.id.cardViewProfile);
        cardCalculator = findViewById(R.id.cardCalculator);
        cardProductList = findViewById(R.id.cardProductList);
        cardFoodDiary = findViewById(R.id.cardFoodDiary);
        cardGoogleFit = findViewById(R.id.cardGoogleFit);

        firebaseAuth = FirebaseAuth.getInstance();

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyAccount();
            }
        });

        cardCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalculator();
            }
        });

        cardProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductList();
            }
        });

        cardFoodDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFoodDiary();
            }
        });

        cardGoogleFit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogleFit();
            }
        });

        cardShare = findViewById(R.id.cardShare);

        cardShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
    }

    public void share(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi there! Join the Calorie Counter");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    public void openMyAccount(){
        Intent intent = new Intent(this, ProfileUpdateActivity.class);
        startActivity(intent);
    }

    public void openCalculator(){
        Intent intent = new Intent(this, CalorieCalculatorActivity.class);
        startActivity(intent);
    }

    public void openProductList(){
        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }

    public void openFoodDiary(){
        Intent intent = new Intent(this, FoodDiaryActivity.class);
        startActivity(intent);
    }

    public void openGoogleFit(){
        Intent intent = new Intent(this, GoogleFitActivity.class);
        startActivity(intent);
    }

    /*Atsijungimas*/
    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user !=null){
        }
        else {
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.home3, menu);
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
