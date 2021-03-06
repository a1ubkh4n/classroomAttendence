package com.a1ubkh4n.app.classroom.adapters;

import android.content.Context;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.a1ubkh4n.app.classroom.R;
import com.a1ubkh4n.app.classroom.interfaces.AdapterClickListener;
import com.a1ubkh4n.app.classroom.interfaces.PopupClickListener;
import com.a1ubkh4n.app.classroom.model.Attendance;

import java.util.ArrayList;

/**
 * Created by Md.Ayub Khan on 02/12/2016.
 */
public class PastAttendancesAdapter extends RecyclerView.Adapter<PastAttendancesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Attendance> attendanceList;

    private AdapterClickListener adapterClickListener;
    private ListPopupWindow listPopupWindow;
    private PopupClickListener popupClickListener;

    public PastAttendancesAdapter(Context context, ArrayList<Attendance> attendanceList) {
        this.context = context;
        this.attendanceList = attendanceList;

        listPopupWindow = new ListPopupWindow(context);
    }

    /**
     * Set on item click listener
     * @param adapterClickListener AdapterClickListener
     */
    public void setAdapterClickListener(AdapterClickListener adapterClickListener) {
        this.adapterClickListener = adapterClickListener;
    }

    /**
     * Set on pop-up men item click listener
     * @param popupClickListener PopupClickListener
     */
    public void setPopupClickListener(PopupClickListener popupClickListener) {
        this.popupClickListener = popupClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.editable_item_small, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Attendance item = attendanceList.get(position);

        viewHolder.counter.setText(String.valueOf(position + 1));
        viewHolder.text.setText(item.getDateTime());
        viewHolder.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listPopupWindow != null) {
                    setListPopUpWindow(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return attendanceList == null ? 0 : attendanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        TextView counter;
        ImageButton settings;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            text = (TextView) itemView.findViewById(R.id.text);
            counter = (TextView) itemView.findViewById(R.id.counter);
            settings = (ImageButton) itemView.findViewById(R.id.settings);
        }

        @Override
        public void onClick(View v) {
            if (adapterClickListener != null) {
                adapterClickListener.OnItemClick(getAdapterPosition());
            }
        }
    }

    /**
     * List pop up menu window
     * @param anchor View
     * @param attendancePosition List item's position
     */
    private void setListPopUpWindow(View anchor, final int attendancePosition) {
        listPopupWindow.dismiss();

        listPopupWindow.setAdapter(new ArrayAdapter(context, android.R.layout.simple_list_item_1,
                context.getResources().getStringArray(R.array.edit_past_attendance)));
        listPopupWindow.setAnchorView(anchor);
        listPopupWindow.setContentWidth(context.getResources()
                .getInteger(R.integer.list_pop_up_width));
        listPopupWindow.setDropDownGravity(Gravity.LEFT);
        listPopupWindow.setModal(true);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int menuItemPosition, long id) {
                if (popupClickListener != null) {
                    popupClickListener.OnPopupClick(attendancePosition, menuItemPosition);
                }

                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.show();
    }

}