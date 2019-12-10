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

import edu.ktu.calorie_counter.EditProductActivity;
import edu.ktu.calorie_counter.Model.Listdata;
import edu.ktu.calorie_counter.R;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyHolder> implements Filterable
{

    List<Listdata> productList;
    private Context context;
    public ProductListAdapter(List<Listdata> productList, Context context)
    {
        this.context=context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);

        MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        Listdata data= productList.get(position);
        myHolder.title.setText(data.getTitle());
        myHolder.calorie.setText(data.getCalorie());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class  MyHolder extends RecyclerView.ViewHolder  {
        TextView title,calorie;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            calorie=itemView.findViewById(R.id.calorie);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Listdata listdata= productList.get(getAdapterPosition());
                    Intent i=new Intent(context, EditProductActivity.class);
                    i.putExtra("id",listdata.id);
                    i.putExtra("title",listdata.title);
                    i.putExtra("calorie",listdata.calorie);
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
            List<Listdata> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(productList);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Listdata item : productList){
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
            productList.clear();
            productList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
