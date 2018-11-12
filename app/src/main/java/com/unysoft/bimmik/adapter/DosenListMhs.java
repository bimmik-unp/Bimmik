package com.unysoft.bimmik.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.unysoft.bimmik.R;

import java.util.ArrayList;
import java.util.List;

public class DosenListMhs extends ArrayAdapter<ImageModel> {

    //the list values in the List of type hero
    List<ImageModel> heroList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    //constructor initializing the values
    public DosenListMhs(Context context, int resource, List<ImageModel> heroList) {
        super(context, resource, heroList);
        this.context = context;
        this.resource = resource;
        this.heroList = heroList;
    }

    //this will return the ListView Item as a View
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        //getting the view elements of the list from the view
        ImageView imageView = view.findViewById(R.id.dosen_list_img_mhs);
        TextView textViewName = view.findViewById(R.id.dosen_list_nm_mhs);

        //getting the hero of the specified position
        ImageModel hero = heroList.get(position);

        //adding values to the list item
        imageView.setImageDrawable(context.getResources().getDrawable(hero.getImgMhs()));
        textViewName.setText(hero.getNmMhs());

        //adding a click listener to the button to remove item from the list
//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //we will call this method to remove the selected value from the list
//                //we are passing the position which is to be removed in the method
//                removeHero(position);
//            }
//        });

        //finally returning the view
        return view;
    }

    //this method will remove the item from the list
//    private void removeHero(final int position) {
//        //Creating an alert dialog to confirm the deletion
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Are you sure you want to delete this?");
//
//        //if the response is positive in the alert
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                //removing the item
//                heroList.remove(position);
//
//                //reloading the list
//                notifyDataSetChanged();
//            }
//        });
//
//        //if response is negative nothing is being done
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//
//        //creating and displaying the alert dialog
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
}