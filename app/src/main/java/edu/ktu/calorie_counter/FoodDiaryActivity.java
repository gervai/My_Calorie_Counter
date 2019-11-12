package edu.ktu.calorie_counter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FoodDiaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diary);

        initalizeFood();
    }

    private void initalizeFood(){
        ImageView imageViewAddBreakfast = (ImageView)findViewById(R.id.imageViewBreakfastAdd);
        imageViewAddBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood(0); // 0 ==  Breakfast
            }
        });
    }

    private void addFood(int mealNumber){

    }
}
