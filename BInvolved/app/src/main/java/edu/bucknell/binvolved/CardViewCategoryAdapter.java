package edu.bucknell.binvolved;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.content.Context;

import java.util.List;

public class CardViewCategoryAdapter extends RecyclerView.Adapter<CardViewCategoryAdapter.PersonViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView categoryName;
        ImageView categoryPhoto;
        Button categoryOptionShortcut;

        private final Context context;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            categoryName = (TextView)itemView.findViewById(R.id.category_name);
            categoryPhoto = (ImageView)itemView.findViewById(R.id.category_photo);
            categoryOptionShortcut = (Button)itemView.findViewById(R.id.category_option_shortcut);

            context = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            System.out.println("IT GOT TO onClick()");
            final Intent intent;

            intent = new Intent(context, IndividualCategoryActivity.class);
            intent.putExtra("Category Name", this.categoryName.getText().toString());
            context.startActivity(intent);
        }
    }

    List<Category> categories;

    CardViewCategoryAdapter(Context context, List<Category> categories){
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
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
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {
        personViewHolder.categoryName.setText(categories.get(i).getName());
        personViewHolder.categoryPhoto.setImageResource(categories.get(i).getSmallPhotoID());
        personViewHolder.categoryOptionShortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CardViewCategoryAdapter: category shortcut option pressed");
            }
        });

        /*
        personViewHolder.cv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("GOT HERE!!!");
                Bundle localBundle = new Bundle();
                //Intent localIntent = new Intent(personViewHolder.context, IndividualCategoryActivity.class);
                //localIntent.putExtra("Category Name", personViewHolder.categoryName.getText().toString());
                //personViewHolder.context.startActivity(localIntent);
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
