package edu.bucknell.binvolved;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.content.Context;

import java.util.Calendar;
import java.util.List;

public class RVEventAdapter extends RecyclerView.Adapter<RVEventAdapter.PersonViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView eventName;
        TextView eventDateTime;
        ImageView eventPhoto;
        Button eventOptionShortcut;

        private final Context context;

        PersonViewHolder(View itemView) {
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

    List<Event> events;

    RVEventAdapter(Context context, List<Event> events) {
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.events = events;
    }



    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_card_view, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.eventName.setText(events.get(i).getName());

        personViewHolder.eventDateTime.setText(events.get(i).getDateAndTime());

        personViewHolder.eventPhoto.setImageResource(events.get(i).getPhotoID());

        personViewHolder.eventOptionShortcut.setOnClickListener(new View.OnClickListener() {
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
