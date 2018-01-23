package com.incon.service.ui.status.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.incon.service.R;
import com.incon.service.apimodel.components.calendar.CalendarDateModel;
import com.incon.service.callbacks.IClickCallback;

import java.util.ArrayList;


/**
 * Created on 6/12/16.
 *
 */
public class HorizontalCalendarAdapter extends RecyclerView.Adapter
        <HorizontalCalendarAdapter.CalendarViewHolder> {
    private ArrayList<CalendarDateModel> datesList;
    private IClickCallback clickCallback;

    public void setClickCallback(IClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public void setCalendarDates(ArrayList<CalendarDateModel> datesList) {
        this.datesList = datesList;
        notifyDataSetChanged();
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_horizontal_calendar, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(CalendarViewHolder holder, int position) {
        CalendarDateModel movie = datesList.get(position);
        holder.dayOfMonth.setText(String.valueOf(movie.getDayOfMonth()));
        holder.weekDay.setText(movie.getDayOfWeek());
    }

    @Override
    public int getItemCount() {
        return datesList.size();
    }

    class CalendarViewHolder extends RecyclerView.ViewHolder {
        private TextView dayOfMonth, weekDay;

        private CalendarViewHolder(View itemView) {
            super(itemView);
            dayOfMonth = (TextView) itemView.findViewById(R.id.day_of_month);
            weekDay = (TextView) itemView.findViewById(R.id.week_day);
            itemView.setOnClickListener(onClickListener);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                clickCallback.onClickPosition(getAdapterPosition());
            }
        };
    }
}
