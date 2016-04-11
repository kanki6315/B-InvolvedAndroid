package edu.bucknell.binvolved;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class RVCategoryAdapter extends RecyclerView.Adapter<RVCategoryAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView categoryName;
        ImageView categoryPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            categoryName = (TextView)itemView.findViewById(R.id.category_name);
            categoryPhoto = (ImageView)itemView.findViewById(R.id.category_photo);
        }
    }

    List<Category> categories;

    RVCategoryAdapter(List<Category> categories){
        this.categories = categories;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_card_view, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.categoryName.setText(categories.get(i).name);
        personViewHolder.categoryPhoto.setImageResource(R.drawable.ace);
        //personViewHolder.categoryPhoto.setImageResource(categories.get(i).smallPhotoID);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
