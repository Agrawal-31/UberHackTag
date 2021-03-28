package com.uber.lastmile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.uber.lastmile.R;
import com.uber.lastmile.models.RouteOption;
import com.uber.lastmile.utils.Utils;

import java.util.List;

public class RouteAdapter extends ArrayAdapter implements Filterable {
    Context context;
    List<RouteOption> routeOptionList;

    public RouteAdapter(Context context, List<RouteOption> list) {
        super(context, 0, list);
        this.routeOptionList = list;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        TextView sourceTextView = rowView.findViewById(R.id.fromText);
        TextView destinationTextView = rowView.findViewById(R.id.toText);
        TextView rewardTextView = rowView.findViewById(R.id.rewardText);
        TextView timeTextView = rowView.findViewById(R.id.timeText);

        RouteOption routeOption = routeOptionList.get(position);
        sourceTextView.setText(Utils.stringTrimmer(routeOption.getFrom()));
        destinationTextView.setText(Utils.stringTrimmer(routeOption.getTo()));
        rewardTextView.setText(String.valueOf(routeOption.getReward()));
        timeTextView.setText(Utils.secondsToString(routeOption.getDuration()));

        rowView.setLayoutParams(new ListView.LayoutParams(
                ListView.LayoutParams.MATCH_PARENT,
                ListView.LayoutParams.WRAP_CONTENT
        ));

        return rowView;
    }


}