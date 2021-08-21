package ir.dotprint.serversideprogramming.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ir.dotprint.serversideprogramming.MassageItem;
import ir.dotprint.serversideprogramming.R;

/**
 * Created by Arash on 5/2/2019.
 */

public class MassageAdapter extends RecyclerView.Adapter<MassageAdapter.MassageViewHolder>{



    ArrayList<MassageItem> massageItems;
    Context context;

    public MassageAdapter(ArrayList<MassageItem> massageItems, Context context) {
        this.massageItems = massageItems;
        this.context = context;
    }

    @Override
    public MassageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.massagelayout,parent,false);
        return new MassageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MassageViewHolder holder, int position) {
        MassageItem massageItem = massageItems.get(position);
        holder.user.setText(massageItem.getUser());
        holder.massage.setText(massageItem.getMassage());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return massageItems.size();
    }

    public class MassageViewHolder extends RecyclerView.ViewHolder {
        TextView user;
        TextView massage;
        CardView card;

        public MassageViewHolder(View itemView) {
            super(itemView);
            user=(TextView)itemView.findViewById(R.id.txtuser);
            massage=(TextView)itemView.findViewById(R.id.txtmassage);
            card=(CardView)itemView.findViewById(R.id.card);
        }
    }
}
