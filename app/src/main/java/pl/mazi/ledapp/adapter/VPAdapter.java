package pl.mazi.ledapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import pl.mazi.ledapp.R;

import java.util.ArrayList;

public class VPAdapter extends FragmentPagerAdapter {

    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////
    private ArrayList<Fragment> fragmentArrayList;
    private ArrayList<String> titleArrayList;
    private ArrayList<Integer> iconArrayList;
    private Context context;


    /////////////////////////////////////////////////////////////////
    //// CONSTRUCTOR
    /////////////////////////////////////////////////////////////////
    public VPAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        fragmentArrayList = new ArrayList<>();
        titleArrayList = new ArrayList<>();
        iconArrayList = new ArrayList<>();
        this.context = context;
    }

    /////////////////////////////////////////////////////////////////
    //// OVERRIDE METHODS
    /////////////////////////////////////////////////////////////////
    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Return fragment from arraylist
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        // Return fragment array list size
        return fragmentArrayList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // Initialize drawable
        Drawable drawable = ContextCompat.getDrawable(context,iconArrayList.get(position));

        // Set bound
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());

        // Initialize spannable spring
        SpannableString spannableString = new SpannableString(" ");

        // Initialize image span
        ImageSpan imageSpan = new ImageSpan(drawable,ImageSpan.ALIGN_BOTTOM);

        // Set span
        spannableString.setSpan(imageSpan,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Return spannable string
        return spannableString;
    }


    /////////////////////////////////////////////////////////////////
    //// MY METHODS
    /////////////////////////////////////////////////////////////////
    public void addNewFragment(Fragment fragment, String title, int icon){
        // Add new fragment to fragment list
        fragmentArrayList.add(fragment);

        // Add title to new fragment list
        titleArrayList.add(title);

        // Add icon to new fragment
        iconArrayList.add(icon);
    }
}
