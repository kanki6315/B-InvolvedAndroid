package edu.bucknell.binvolved;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListViewCategoryAdapter extends RecyclerView.Adapter<ListViewCategoryAdapter.CategoryViewHolder> {

    public static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView listItem;
        TextView categoryName;
        ImageView categoryPhoto;
        Button categoryOptionShortcut;

        private final Context context;

        CategoryViewHolder(View itemView) {
            super(itemView);
            listItem = (CardView)itemView.findViewById(R.id.list_item);
            categoryName = (TextView)itemView.findViewById(R.id.category_name);
            categoryPhoto = (ImageView)itemView.findViewById(R.id.category_photo);
            categoryOptionShortcut = (Button)itemView.findViewById(R.id.category_option_shortcut);

            context = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            System.out.println("GOT TO onClick() in ListViewCategoryAdapter");
            Intent intent = new Intent(context, IndividualCategoryActivity.class);
            intent.putExtra("Category Name", this.categoryName.getText().toString());
            context.startActivity(intent);
        }
    }

    private Context context;
    private LayoutInflater inflater;

    // list of all Categories to display
    List<Category> categories;

    /**
     * Constructor for the adapter.
     *
     * @param context           Context
     * @param categories        list of Categories
     */
    ListViewCategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.categories = categories;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * Sets the layout to be category_list_item
     *
     * @param viewGroup         ViewGroup instance
     * @param i                 index
     * @return                  CategoryViewHolder object
     */
    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_list_item, viewGroup, false);
        return new CategoryViewHolder(v);
    }

    /**
     * Configures the layout to display the correct values.
     *
     * @param categoryViewHolder        CategoryViewHolder object
     * @param i                         index
     */
    @Override
    public void onBindViewHolder(CategoryViewHolder categoryViewHolder, int i) {
        categoryViewHolder.categoryName.setText(categories.get(i).getName());
        categoryViewHolder.categoryPhoto.setImageResource(categories.get(i).getSmallPhotoID());

        categoryViewHolder.categoryOptionShortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.popup_category, popupMenu.getMenu());
                popupMenu.show();
                //System.out.println("category shortcut option pressed");
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
