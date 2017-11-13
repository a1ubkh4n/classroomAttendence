package com.a1ubkh4n.app.classroom.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a1ubkh4n.app.classroom.R;
import com.a1ubkh4n.app.classroom.interfaces.AdapterClickListener;
import com.a1ubkh4n.app.classroom.model.Student;

import java.util.ArrayList;

/**
 * Created by Md.Ayub Khan on 02/12/2016.
 */
public class TakeAttendanceAdapter extends RecyclerView.Adapter<TakeAttendanceAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Student> studentList;

    private AdapterClickListener adapterClickListener;

    public TakeAttendanceAdapter(Context context, ArrayList<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
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
                .inflate(R.layout.checkable_text_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Student item = studentList.get(position);

        viewHolder.text.setText(item.getName());
        if (item.isPresent()) {
            viewHolder.checkBox.setImageResource(R.drawable.ic_check_box);
            viewHolder.checkBox.setColorFilter(ContextCompat.getColor(context, R.color.colourAccent));
        } else {
            viewHolder.checkBox.setImageResource(R.drawable.ic_check_box_outline);
            viewHolder.checkBox.setColorFilter(ContextCompat.getColor(context, R.color.darkGrey));
        }
    }

    @Override
    public int getItemCount() {
        return studentList == null ? 0 : studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        ImageView checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            text = (TextView) itemView.findViewById(R.id.text);
            checkBox = (ImageView) itemView.findViewById(R.id.checkBox);
        }

        @Override
        public void onClick(View v) {
            if (adapterClickListener != null) {
                adapterClickListener.OnItemClick(getAdapterPosition());
            }
        }
    }

}