package edu.bucknell.binvolved;

import java.util.ArrayList;
import java.util.TreeSet;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.BaseAdapter;
import android.view.View.OnClickListener;

/**
 * Adapter for the List View of Events.
 *
 * Created by gilbertkim on 4/20/16.
 */
public class ListViewEventAdapter extends BaseAdapter {

    public static class ViewHolder{
        public TextView name;
        public TextView time;
        public TextView location;
        public ImageView image;
        public Button eventOptionShortcut;
        public String eventDateTime;
    }

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private ArrayList<String[]> mData = new ArrayList<String[]>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    private LayoutInflater mInflater;

    public ListViewEventAdapter(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Adds all of the necessary information to display and unique identify the Event.
     *
     * @param name              the Event name
     * @param time              the Event starting time
     * @param location          the Event location
     * @param photo             the Event photo
     * @param dateAndTime       the Event starting date and time
     */
    public void addItem(String name, String time, String location, int photo, String dateAndTime) {
        String[] values = {name, time, location, Integer.toString(photo), dateAndTime};
        mData.add(values);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(String item) {
        String[] values = {item};
        mData.add(values);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String[] getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.list_item, null);
                    holder.name = (TextView) convertView.findViewById(R.id.event_name);
                    holder.time = (TextView) convertView.findViewById(R.id.event_time);
                    holder.location = (TextView) convertView.findViewById(R.id.event_location);
                    holder.image = (ImageView) convertView.findViewById(R.id.event_photo);
                    holder.eventOptionShortcut = (Button) convertView.findViewById(R.id.event_option_shortcut);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.list_section_title, null);
                    holder.name = (TextView) convertView.findViewById(R.id.list_section_title);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String [] values = mData.get(position);
        holder.name.setText(values[0]);
        // if not a section title, add more information
        if (values.length > 1) {
            holder.time.setText(values[1]);
            holder.location.setText(values[2]);
            holder.image.setImageResource(Integer.parseInt(values[3]));
            holder.eventDateTime = values[4];

            holder.eventOptionShortcut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("event shortcut option pressed");
                }
            });

            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("GOT HERE!!!");
                    Intent intent = new Intent(v.getContext(), IndividualEventActivity.class);
                    intent.putExtra("Event Name", mData.get(position)[0]);
                    intent.putExtra("Event Date", mData.get(position)[4]);
                    v.getContext().startActivity(intent);
                }
            });
        }
        return convertView;
    }
}
