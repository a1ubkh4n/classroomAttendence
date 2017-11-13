package com.a1ubkh4n.app.classroom.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a1ubkh4n.app.classroom.R;
import com.a1ubkh4n.app.classroom.interfaces.AdapterClickListener;
import com.a1ubkh4n.app.classroom.model.Classroom;

import java.util.ArrayList;

/**
 * Created by Md.Ayub Khan on 02/12/2016.
 */
public class OperateClassroomsAdapter extends RecyclerView.Adapter<OperateClassroomsAdapter.ViewHolder> {
    private ArrayList<Classroom> classroomList;

    private AdapterClickListener adapterClickListener;

    public OperateClassroomsAdapter(ArrayList<Classroom> classroomList) {
        this.classroomList = classroomList;
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
                .inflate(R.layout.operatable_item_big, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Classroom item = classroomList.get(position);

        viewHolder.text.setText(item.getName());
        viewHolder.counter.setText(String.valueOf(item.getStudentNumber()));
    }

    @Override
    public int getItemCount() {
        return classroomList == null ? 0 : classroomList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        TextView counter;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            text = (TextView) itemView.findViewById(R.id.text);
            counter = (TextView) itemView.findViewById(R.id.counter);
        }

        @Override
        public void onClick(View v) {
            if (adapterClickListener != null) {
                adapterClickListener.OnItemClick(getAdapterPosition());
            }
        }
    }

}