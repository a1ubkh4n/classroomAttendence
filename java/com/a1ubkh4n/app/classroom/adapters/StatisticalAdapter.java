package com.a1ubkh4n.app.classroom.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a1ubkh4n.app.classroom.R;
import com.a1ubkh4n.app.classroom.interfaces.AdapterClickListener;
import com.a1ubkh4n.app.classroom.model.AttendanceStatistics;

import java.util.ArrayList;

/**
 * CCreated by Md.Ayub Khan on 02/12/2016.
 */
public class StatisticalAdapter extends RecyclerView.Adapter<StatisticalAdapter.ViewHolder> {
    private ArrayList<AttendanceStatistics> attendanceList;

    private AdapterClickListener adapterClickListener;

    public StatisticalAdapter(ArrayList<AttendanceStatistics> attendanceList) {
        this.attendanceList = attendanceList;
    }

    /**
     * Set on item click listener
     * @param adapterClickListener AdapterClickListener
     */
    public void setAdapterClickListener(AdapterClickListener adapterClickListener) {
        this.adapterClickListener = adapterClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hash_text_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        AttendanceStatistics item = attendanceList.get(position);

        viewHolder.key.setText(item.getStudentName());
        viewHolder.valuePercentage.setText(item.getPresencePercentage() + "%");
        viewHolder.valueNumeric.setText(item.getAttendedClasses()
                + "/" + item.getAvailableClasses());
    }

    @Override
    public int getItemCount() {
        return attendanceList == null ? 0 : attendanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView key;
        TextView valuePercentage;
        TextView valueNumeric;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            key = (TextView) itemView.findViewById(R.id.key);
            valuePercentage = (TextView) itemView.findViewById(R.id.valuePercentage);
            valueNumeric = (TextView) itemView.findViewById(R.id.valueNumeric);
        }

        @Override
        public void onClick(View v) {
            if (adapterClickListener != null) {
                adapterClickListener.OnItemClick(getAdapterPosition());
            }
        }
    }

}