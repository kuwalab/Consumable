package net.kuwalab.android.consumable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

public class ConsumableAdapter extends SimpleAdapter {
    private LayoutInflater layoutInflater;

    public ConsumableAdapter(Context context, List<? extends Map<String, ?>> data,
                             int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        layoutInflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.consumable_row, parent, false);
        }

        ListView listView = (ListView) parent;
        Map<String, Object> data = (Map<String, Object>) listView.getItemAtPosition(position);

        AppCompatTextView consumableName =
            (AppCompatTextView) convertView.findViewById(R.id.consumableName);
        consumableName.setText(data.get("consumableName").toString());

        AppCompatTextView consumableDate =
            (AppCompatTextView) convertView.findViewById(R.id.consumableDate);
        consumableDate.setText(data.get("consumableDate").toString());

        AppCompatTextView consumablePrice =
            (AppCompatTextView) convertView.findViewById(R.id.consumablePrice);
        consumablePrice.setText(data.get("consumablePrice").toString());

        return convertView;
    }
}
