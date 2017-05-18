package com.ramonem.spotysearch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ramonem on 18-05-17.
 */

public class ArtistAdapter extends ArrayAdapter<Artist> {

    private List<Artist> lista;
    private Context context;

    public ArtistAdapter(List<Artist> lista, Context context) {
        super(context, R.layout.row_item, lista);
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row_item, parent, false);
        }

        TextView txtName = (TextView) rowView.findViewById(R.id.txtName);
        TextView txtFollowers = (TextView) rowView.findViewById(R.id.txtFollowers);
        TextView txtType = (TextView) rowView.findViewById(R.id.txtType);
        Button btnPlay = (Button) rowView.findViewById(R.id.btnPlay);

        final Artist l = lista.get(position);

        txtName.setText(l.getName());
        txtFollowers.setText("Followers: " + String.valueOf(l.getFollowers()));
        txtType.setText("Type: " + l.getType());
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(l.getUrl()));
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(browserIntent);
            }
        });
        return rowView;
    }
}
