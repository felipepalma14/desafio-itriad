package com.felipe.palma.githubtrends_itriad.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.felipe.palma.githubtrends_itriad.R;
import com.felipe.palma.githubtrends_itriad.domain.model.GithubUser;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe Palma on 07/08/2019.
 */
public class GithubUserAdapter extends RecyclerView.Adapter<GithubUserAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<GithubUser> itemsList;
    private ArrayList<GithubUser> itemsListFiltered;
    private RecyclerItemClickListener listener;



    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserName;
        ImageView imgUserRepo;

        ViewHolder(View view) {
            super(view);
            txtUserName = view.findViewById(R.id.txt_user_name);

            imgUserRepo = view.findViewById(R.id.img_user_pull);


            view.setOnClickListener(view1 -> listener.onItemClick(itemsListFiltered.get(getAdapterPosition())));
        }
    }

    public GithubUserAdapter(Context context, ArrayList<GithubUser> itemsList, RecyclerItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.itemsList = itemsList;
        this.itemsListFiltered = itemsList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_github_user, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final GithubUser item = this.itemsListFiltered.get(position);

        holder.txtUserName.setText(item.getLogin());





        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(20)
                .oval(true)
                .build();

        Picasso.with(context)
                .load(item.getAvatarUrl())
                .placeholder(R.drawable.ic_loading)
                .fit()
                .transform(transformation)
                .into(holder.imgUserRepo);


    }

    public void addRepoItems(List<GithubUser> items) {
        itemsListFiltered.addAll(items);
        //notifyDataSetChanged();
    }

    public void clearItems() {
        itemsListFiltered.clear();
        //notifyDataSetChanged();
    }

    public ArrayList<GithubUser> getItems() {
        return itemsListFiltered;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    itemsListFiltered = itemsList;
                } else {
                    ArrayList<GithubUser> filteredList = new ArrayList<>();
                    for (GithubUser item : itemsList) {
                        if (item.getLogin().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    itemsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = itemsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemsListFiltered = (ArrayList<GithubUser>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return itemsListFiltered.size();
    }

}
