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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;

import java.util.List;

public class CardViewCategoryAdapter extends RecyclerView.Adapter<CardViewCategoryAdapter.CategoryViewHolder> {

    //private Context context;
    //private LayoutInflater inflater;
    List<Category> categories;

    public static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView categoryName;
        ImageView categoryPhoto;
        Button categoryOptionShortcut;

        private final Context context;

        CategoryViewHolder(View itemView) {
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

    CardViewCategoryAdapter(Context context, List<Category> categories){
        //this.context = context;
        //this.inflater = LayoutInflater.from(this.context);
        this.categories = categories;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_card_view, viewGroup, false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder categoryViewHolder, int i) {
        final int copyI = i;
        categoryViewHolder.categoryName.setText(categories.get(i).getName());
        categoryViewHolder.categoryPhoto.setImageResource(categories.get(i).getSmallPhotoID());
        categoryViewHolder.categoryOptionShortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CardViewCategoryAdapter: category shortcut option pressed");
                // show unfollow option
                if (Category.getFollowingCategories().contains(Category.getCategoryWithName(categories.get(copyI).getName()))) {

                } else {
                    //show follow option

                }
            }
        });
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
    */

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
