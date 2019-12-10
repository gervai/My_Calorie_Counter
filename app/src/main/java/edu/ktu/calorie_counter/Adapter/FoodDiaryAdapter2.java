package edu.ktu.calorie_counter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ktu.calorie_counter.Model.Listdata3;
import edu.ktu.calorie_counter.R;

public class FoodDiaryAdapter2 extends RecyclerView.Adapter<FoodDiaryAdapter2.MyViewHolder> {

    public List<Listdata3> list;

    public FoodDiaryAdapter2(List<Listdata3> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item3, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.calorie.setText(list.get(position).getCalorie());
        holder.count.setText(list.get(position).getCount());
        holder.position = position;
        holder.date.setText(list.get(position).getDate());
        holder.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, calorie, count, date;
        Integer position =-1;
        View rootView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.rootView = itemView;
            title = itemView.findViewById(R.id.iddddd);
            calorie = itemView.findViewById(R.id.calorie);
            count = itemView.findViewById(R.id.count);
            date = itemView.findViewById(R.id.date);

            itemView.setTag(position);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }
        public void setTag(int position){
            rootView.setTag((int)position);
        }
    }
}
