package edu.bucknell.binvolved;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import java.util.Calendar;
import java.util.List;

public class RVEventAdapter extends RecyclerView.Adapter<RVEventAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView eventName;
        TextView eventDateTime;
        ImageView eventPhoto;
        Button eventOptionShortcut;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            eventName = (TextView)itemView.findViewById(R.id.event_name);
            eventDateTime = (TextView)itemView.findViewById(R.id.event_date_time);
            eventPhoto = (ImageView)itemView.findViewById(R.id.event_photo);
            eventOptionShortcut = (Button)itemView.findViewById(R.id.event_option_shortcut);
        }
    }

    List<Event> events;

    RVEventAdapter(List<Event> events){
        this.events = events;
    }

    String[] days = {"", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

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

        Calendar calendar = events.get(i).start;
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String extension = "PM";
        if (hour == hourOfDay) {
            extension = "AM";
        }
        if (minute != 0) {
            extension = ":" + minute + extension;
        }

        String dateAndTime = days[day] + " " + (month+1) + "/" + date + " " + hour + extension;

        personViewHolder.eventDateTime.setText(dateAndTime);

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
