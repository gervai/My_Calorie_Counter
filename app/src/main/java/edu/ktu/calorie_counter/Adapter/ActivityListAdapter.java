package edu.ktu.calorie_counter.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ktu.calorie_counter.EditProductActivity;
import edu.ktu.calorie_counter.Model.Listdata;
import edu.ktu.calorie_counter.R;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.MyHolder>
{

    List<Listdata> activityList;
    private Context context;
    public ActivityListAdapter(List<Listdata> activityList, Context context)
    {
        this.context=context;
        this.activityList = activityList;
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
        Listdata data= activityList.get(position);
        myHolder.title.setText(data.getTitle());
        myHolder.desc.setText(data.getDesc());
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }

    class  MyHolder extends RecyclerView.ViewHolder  {
        TextView title,desc;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.desc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Listdata listdata= activityList.get(getAdapterPosition());
                    Intent i=new Intent(context, EditProductActivity.class);
                    i.putExtra("id",listdata.id);
                    i.putExtra("title",listdata.title);
                    i.putExtra("desc",listdata.desc);
                    context.startActivity(i);
                    }
            });

        }


    }
}
