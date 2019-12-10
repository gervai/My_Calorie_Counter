package edu.ktu.calorie_counter.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.ktu.calorie_counter.EditFoodActivity;
import edu.ktu.calorie_counter.Model.Listdata2;
import edu.ktu.calorie_counter.R;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyHolder> implements Filterable
{

    List<Listdata2> foodList;
    private Context context;
    public FoodAdapter(List<Listdata2> foodList, Context context)
    {
        this.context=context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item2,viewGroup,false);

        MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        Listdata2 data= foodList.get(position);
        myHolder.title.setText(data.getTitle());
        myHolder.calorie.setText(data.getCalorie());
        myHolder.count.setText(data.getCount());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class  MyHolder extends RecyclerView.ViewHolder  {
        TextView title,calorie,count;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            calorie=itemView.findViewById(R.id.calorie);
            count=itemView.findViewById(R.id.count);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Listdata2 listdata= foodList.get(getAdapterPosition());
                    Intent i=new Intent(context, EditFoodActivity.class);
                    i.putExtra("id",listdata.id);
                    i.putExtra("title",listdata.title);
                    i.putExtra("calorie",listdata.calorie);
                    i.putExtra("count",listdata.count);
                    context.startActivity(i);
                    }
            });
        }
    }

    /*Paieska*/
    @Override
    public Filter getFilter(){
        return listFilter;
    }

    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Listdata2> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(foodList);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Listdata2 item : foodList){
                    if (item.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            foodList.clear();
            foodList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
