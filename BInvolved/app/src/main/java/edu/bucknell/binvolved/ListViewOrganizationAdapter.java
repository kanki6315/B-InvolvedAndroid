package edu.bucknell.binvolved;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter Class to populate the list view of Organizations.
 *
 * Created by gilbertkim on 4/19/16.
 */
public class ListViewOrganizationAdapter extends RecyclerView.Adapter<ListViewOrganizationAdapter.OrganizationViewHolder> {

    public static class OrganizationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView listItem;
        TextView organizationName;
        ImageView organizationPhoto;
        Button organizationOptionShortcut;

        private final Context context;

        OrganizationViewHolder(View itemView) {
            super(itemView);
            listItem = (CardView)itemView.findViewById(R.id.list_item);
            organizationName = (TextView)itemView.findViewById(R.id.organization_name);
            organizationPhoto = (ImageView)itemView.findViewById(R.id.organization_photo);
            organizationOptionShortcut = (Button)itemView.findViewById(R.id.organization_option_shortcut);

            context = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            System.out.println("GOT TO onClick() in ListViewOrganizationAdapter");
            Intent intent = new Intent(context, IndividualOrganizationActivity.class);
            intent.putExtra("Organization Name", this.organizationName.getText().toString());
            context.startActivity(intent);
        }
    }

    private Context context;
    private LayoutInflater inflater;

    // list of all Organizations to display
    List<Organization> organizations;

    /**
     * Constructor for the adapter.
     *
     * @param context               Context
     * @param organizations         list of Organizations
     */
    ListViewOrganizationAdapter(Context context, List<Organization> organizations) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.organizations = organizations;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * Sets the layout to be organization_list_item.
     *
     * @param viewGroup     the ViewGroup instance
     * @param i             index
     * @return              OrganizationViewHolder object
     */
    @Override
    public OrganizationViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.organization_list_item, viewGroup, false);
        return new OrganizationViewHolder(v);
    }

    /**
     * Configures the layout to display the correct values.
     *
     * @param organizationViewHolder       OrganizationViewHolder object
     * @param i                     index
     */
    @Override
    public void onBindViewHolder(OrganizationViewHolder organizationViewHolder, int i) {
        organizationViewHolder.organizationName.setText(organizations.get(i).getName());
        organizationViewHolder.organizationPhoto.setImageResource(organizations.get(i).getImages()[0]);

        organizationViewHolder.organizationOptionShortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("event shortcut option pressed");
            }
        });
    }

    @Override
    public int getItemCount() {
        return organizations.size();
    }
}
