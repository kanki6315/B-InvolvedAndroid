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

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView eventName;
        TextView eventDateTime;
        ImageView eventPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            eventName = (TextView)itemView.findViewById(R.id.event_name);
            eventDateTime = (TextView)itemView.findViewById(R.id.event_date_time);
            eventPhoto = (ImageView)itemView.findViewById(R.id.event_photo);
        }
    }

    List<Event> events;

    RVAdapter(List<Event> events){
        this.events = events;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.eventName.setText(events.get(i).name);

        // TO-DO: UPDATE THIS
        Calendar calendar = events.get(i).start;
        String date = calendar.MONTH + "/" + calendar.DATE + " " + calendar.HOUR;

        personViewHolder.eventDateTime.setText(date);

        personViewHolder.eventPhoto.setImageResource(events.get(i).photoID);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
