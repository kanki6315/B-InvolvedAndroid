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

public class ListViewEventAdapter extends RecyclerView.Adapter<ListViewEventAdapter.EventViewHolder> {

    public static class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView listItem;
        TextView eventName;
        TextView eventDateTime;
        ImageView eventPhoto;
        Button eventOptionShortcut;

        private final Context context;

        EventViewHolder(View itemView) {
            super(itemView);
            listItem = (CardView)itemView.findViewById(R.id.list_item);
            eventName = (TextView)itemView.findViewById(R.id.event_name);
            eventDateTime = (TextView)itemView.findViewById(R.id.event_date_time);
            eventPhoto = (ImageView)itemView.findViewById(R.id.event_photo);
            eventOptionShortcut = (Button)itemView.findViewById(R.id.event_option_shortcut);

            context = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            System.out.println("GOT TO onClick() in ListViewEventAdapter");
            Intent intent = new Intent(context, IndividualEventActivity.class);
            intent.putExtra("Event Name", this.eventName.getText().toString());
            intent.putExtra("Event Date", this.eventDateTime.getText().toString());
            context.startActivity(intent);
        }
    }

    private Context context;
    private LayoutInflater inflater;

    // list of all Events to display
    List<Event> events;

    /**
     * Constructor for the adapter.
     *
     * @param context       Context
     * @param events        list of Events
     */
    ListViewEventAdapter(Context context, List<Event> events) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.events = events;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * Sets the layout to be event_list_item.
     *
     * @param viewGroup     the ViewGroup instance
     * @param i             index
     * @return              EventViewHolder object
     */
    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_list_item, viewGroup, false);
        return new EventViewHolder(v);
    }

    /**
     * Configures the layout to display the correct values.
     *
     * @param eventViewHolder       EventViewHolder object
     * @param i                     index
     */
    @Override
    public void onBindViewHolder(EventViewHolder eventViewHolder, int i) {
        eventViewHolder.eventName.setText(events.get(i).getName());
        eventViewHolder.eventDateTime.setText(events.get(i).getDateAndTime());
        eventViewHolder.eventPhoto.setImageResource(events.get(i).getPhotoID());

        eventViewHolder.eventOptionShortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("event shortcut option pressed");
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
