package edu.bucknell.binvolved;

import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.content.Context;

import java.util.List;

public class CardViewEventAdapter extends RecyclerView.Adapter<CardViewEventAdapter.EventViewHolder> {

    private Context context;
    //private LayoutInflater inflater;
    List<Event> events;

    public static class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView eventName;
        TextView eventDateTime;
        ImageView eventPhoto;
        Button eventOptionShortcut;

        private final Context context;

        EventViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
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

            System.out.println("IT GOT TO onClick()");
            final Intent intent;

            intent = new Intent(context, IndividualEventActivity.class);
            intent.putExtra("Event Name", this.eventName.getText().toString());
            intent.putExtra("Event Date", this.eventDateTime.getText().toString());
            context.startActivity(intent);
        }
    }

    CardViewEventAdapter(Context context, List<Event> events) {
        this.context = context;
        //this.inflater = LayoutInflater.from(this.context);
        this.events = events;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_card_view, viewGroup, false);
        return new EventViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final EventViewHolder eventViewHolder, int i) {
        eventViewHolder.eventName.setText(events.get(i).getName());
        eventViewHolder.eventDateTime.setText(events.get(i).getDateAndTime());
        eventViewHolder.eventPhoto.setImageResource(events.get(i).getPhotoID());
        eventViewHolder.eventOptionShortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.share) {
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            String sendString = "Hey! I found " + events.get(eventViewHolder.getAdapterPosition()).getName() + " using the B-Involved app. Want to go?";
                            sendIntent.putExtra(Intent.EXTRA_TEXT, sendString);
                            sendIntent.setType("text/plain");
                            context.startActivity(Intent.createChooser(sendIntent, "Send to"));

                        }
                        return false;
                    }
                });
                menuInflater.inflate(R.menu.popup_event, popupMenu.getMenu());
                popupMenu.show();
                System.out.println("CardViewEventAdapter: event shortcut option pressed");
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
