package edu.ktu.calorie_counter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.ktu.calorie_counter.Adapter.ProductListAdapter;
import edu.ktu.calorie_counter.Model.Listdata;

public class ProductListActivity extends AppCompatActivity  {

    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    SharedPreferences sharedPreferences;
    LinearLayoutManager linearLayoutManager;
    DatabaseReference databaseReference;
    private ProductListAdapter adapter;
    List<Listdata> list = new ArrayList<>();
    Context context;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Product list");
        firebaseAuth = FirebaseAuth.getInstance();

        preferences = this.getSharedPreferences("My_Pref", MODE_PRIVATE);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductListActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        final ProductListAdapter productAdapter = new ProductListAdapter(list, this);
        recyclerView.setAdapter(productAdapter);
        FloatingActionButton fab = findViewById(R.id.fab);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Product");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Listdata listdata = dataSnapshot1.getValue(Listdata.class);
                    list.add(listdata);

                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddProductActivity.class));
            }
        });

        sharedPreferences=getSharedPreferences("SortSettings", MODE_PRIVATE);
        String mSorting = sharedPreferences.getString("Sort", "Ascending");

        if(mSorting.equals("Ascending")){
            Collections.sort(list, Listdata.By_TITLE_ASCENDING);
        }
        else if (mSorting.equals("Descending")){
            Collections.sort(list, Listdata.By_TITLE_DESCENDING);
        }
    }

    /*Atsijungimas*/
    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user !=null){
        }
        else {
            startActivity(new Intent(ProductListActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.home, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void search(String str) {
        ArrayList<Listdata>myList = new ArrayList<>();
        for(Listdata object : list)
        {
            if(object.getTitle().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(object);
            }
        }
        ProductListAdapter notesAdapter=new ProductListAdapter(myList, this);
        recyclerView.setAdapter(notesAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sorting){
            showSortDialog();
            return true;
        }
        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSortDialog() {
        String[] sortOption = {"Ascending", "Descending"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort by")
                .setIcon(R.drawable.ic_action_sort)
                .setItems(sortOption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Sort", "Ascending");
                            editor.apply();
                            recreate();

                        } else if (which == 1) {
                            {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Sort", "Descending");
                                editor.apply();
                                recreate();

                            }
                        }
                    }
                });
            builder.show();
        }
    }

