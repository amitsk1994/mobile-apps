package com.x.android.inclassassignment7;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

//kishan singh
//amit s kalyanour
//group - 15

public class MainActivity extends AppCompatActivity implements MyProfileFragment.MyProfileFragmentListener,SelectAvatarFragment.SelectAvatarFragmentListener,DisplayProfileFragment.DisplayProfileFragmentListener {

    private int selectedProfile;
    private User user;
    SharedPreferences sharedPreferences ;//= getSharedPreferences("userInfo" , Context.MODE_PRIVATE);
    SharedPreferences.Editor editor ;//= sharedPreferences.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("userInfo" , Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Log.d("Demo shared p data" , sharedPreferences.getAll()+"");

//        Log.d("Demo shared preference" , sharedPreferences.getInt("image" , -1)+"");
//            editor.clear();
//            editor.apply();
//        Log.d("Demo post delete" , sharedPreferences.getInt("image" , -1)+"");


        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new MyProfileFragment(getApplicationContext()), "MyProfileFragment")
                .commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        sharedPreferences = getSharedPreferences("userInfo" , Context.MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//
//        editor.remove("image");
//        editor.clear();
//        editor.commit();
    }

//    @Override
//    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount()>0){
//            getSupportFragmentManager().popBackStack();
////            SharedPreferences spreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
////            SharedPreferences.Editor editor = spreferences.edit();
////            editor.clear();
////            editor.commit();
//        }else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public void goToSelectAvatar() {
        setTitle("Select Avatar");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new SelectAvatarFragment(), "SelectAvatarFragment")
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void goToDisplayAvatar(User user) {
        setTitle("Display My Profile");
        // this.user = user;
        DisplayProfileFragment displayProfileFragment = new DisplayProfileFragment(getApplicationContext());
//        Bundle args = new Bundle();
//        args.putSerializable(DisplayProfileFragment.USER, user);
//        displayProfileFragment.setArguments(args);
//
//        editor.putString("firstname" , user.getFirstName());
//        editor.putString("lastname" , user.getLastName());
//        editor.putString("department" , user.getDepartment());
//        editor.putString("studentID" , user.getStudentId());

        Gson gson = new Gson();
//        if( gson.toJson("user",User.class)==null)
//            gson.toJson("user",User.class);
//


        editor.apply();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, displayProfileFragment, "DisplayProfileFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToMyProfile(int id) {
        setTitle("My Profile");
        selectedProfile = id;
        Log.d("Demo","Selected id = "+selectedProfile);
        MyProfileFragment myProfileFragment = (MyProfileFragment) getSupportFragmentManager().findFragmentByTag("MyProfileFragment");
        if (myProfileFragment == null) {
            myProfileFragment = new MyProfileFragment(getApplicationContext());
        }
//        Bundle args = new Bundle();
//        args.putInt(MyProfileFragment.IMAGEVALUE,id);
//        myProfileFragment.setArguments(args);
        Gson gson = new Gson();
//        gson.toJson("user",User.class);


        editor.putInt("image" , selectedProfile);
        editor.apply();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, myProfileFragment, "MyProfileFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToProfileScreen() {
        setTitle("My Profile");
        MyProfileFragment myProfileFragment = (MyProfileFragment) getSupportFragmentManager().findFragmentByTag("MyProfileFragment");
        if (myProfileFragment == null) {
            myProfileFragment = new MyProfileFragment(getApplicationContext());
        }
//        Bundle args = new Bundle();
//        args.putInt(MyProfileFragment.IMAGEVALUE,selectedProfile);
//        myProfileFragment.setArguments(args);

       // editor.putInt("image" , selectedProfile);
      //  editor.apply();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, myProfileFragment, "MyProfileFragment")
                .addToBackStack(null)
                .commit();
    }
}
