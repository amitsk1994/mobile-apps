package com.x.android.inclassassignment7;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;

public class MyProfileFragment extends Fragment {


    private MyProfileFragmentListener mListener;
    private Button buttonSave;
    private EditText firstName, lastName, studentId;
    private RadioGroup departmentGroup;
    private ImageView avatarImage;
    private int avatarImageValue;
    private User user;
    public static final String FLAG = "flag";
    public static final String IMAGEVALUE = "imageValue";
    public static final String USER = "user";
    private String flag;
    SharedPreferences sharedPreferences;// = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);

    Context context ;

    public MyProfileFragment( ) {

    }

    @SuppressLint("ValidFragment")
    public MyProfileFragment(Context applicationContext) {
        this.context= applicationContext;
        sharedPreferences = this.context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        buttonSave = view.findViewById(R.id.buttonSave);
        firstName = view.findViewById(R.id.editTextFirstName);
        lastName = view.findViewById(R.id.editTextLastName);
        studentId = view.findViewById(R.id.editTextStudentId);
        departmentGroup = view.findViewById(R.id.radioGroupDepartment);
        avatarImage = view.findViewById(R.id.imageView);
        avatarImage.setImageResource(R.drawable.select_image);
        avatarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToSelectAvatar();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Bitmap bmap = ((BitmapDrawable) avatarImage.getDrawable()).getBitmap();
                Drawable myDrawable = getResources().getDrawable(R.drawable.select_image);
                final Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();

                if (bmap.sameAs(myLogo)) {
                    Toast.makeText(departmentGroup.getContext(), "Select a Profile Image", Toast.LENGTH_LONG).show();
                } else if (firstName.getText().toString() == null || firstName.getText().toString().matches("")) {
                    firstName.setError("Enter the First Name");
                } else if (lastName.getText().toString() == null || lastName.getText().toString().matches("")) {
                    lastName.setError("Enter the Last Name");
                } else if (studentId.getText().toString() == null || studentId.getText().toString().matches("") || studentId.getText().toString().length()!=9) {
                    studentId.setError("Enter the Student Id of 9 digits only");
                } else if (departmentGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(departmentGroup.getContext(), "Select a radio button", Toast.LENGTH_LONG).show();
                } else {
                    int radioButtonID = departmentGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = departmentGroup.findViewById(radioButtonID);
                    Log.d("Radio", "radioButton.getText().toString()" + radioButton.getText().toString());
                    user = new User(firstName.getText().toString(), lastName.getText().toString(), studentId.getText().toString(), avatarImageValue, radioButton.getText().toString());
                    Gson gson=new Gson();
                    String user_JSON=gson.toJson(user);
                    sharedPreferences.edit().putString("user", user_JSON).apply();
                    Log.d("Demo shared pref",sharedPreferences.getAll()+"");
                    Log.d("Demo",user.toString());
                    mListener.goToDisplayAvatar(user);
                }
            }
        });


           // if (sharedPreferences!=null && sharedPreferences.getInt("image",-1)!=-1){
            if(sharedPreferences.getString("user",null)!=null || sharedPreferences.getInt("image", -1)!=-1){

                String user_json=sharedPreferences.getString("user", null);
                Gson gson=new Gson();
                user=gson.fromJson(user_json, User.class);
                if(user!=null){
                    firstName.setText(user.getFirstName() );
                    lastName.setText(user.getLastName());
                    //if(!sharedPreferences.getString("studentID" , "-1").equals("-1"))
                    studentId.setText(user.getStudentId());

                    RadioGroup rg = new RadioGroup(getActivity());
                    RadioButton  rb_bio =  view.findViewById(R.id.radioButtonBIO);
                    RadioButton  rb_sis = view.findViewById(R.id.radioButtonSIS);
                    RadioButton  rb_other =  view.findViewById(R.id.radioButtonOther);
                    RadioButton  rb_cs = view.findViewById(R.id.radioButtonCS);

//              if(rb_bio.getText().toString().equals(sharedPreferences.getString("department","")))
//                    rb_bio.toggle();
//
//                if(rb_sis.getText().toString().equals(sharedPreferences.getString("department","")))
//                    rb_sis.toggle();
//
//                if(rb_other.getText().toString().equals(sharedPreferences.getString("department","")))
//                    rb_other.toggle();
//
//                if(rb_cs.getText().toString().equals(sharedPreferences.getString("department","")))
//                    rb_cs.toggle();
                    if(rb_bio.getText().toString().equals(user.getDepartment()))
                        rb_bio.toggle();

                    if(rb_sis.getText().toString().equals(user.getDepartment()))
                        rb_sis.toggle();

                    if(rb_other.getText().toString().equals(user.getDepartment()))
                        rb_other.toggle();

                    if(rb_cs.getText().toString().equals(user.getDepartment()))
                        rb_cs.toggle();
                }


                                    Log.d("Demo avatar image before",avatarImage+"");
                avatarImage.setImageResource(sharedPreferences.getInt("image",-1));
                avatarImageValue=sharedPreferences.getInt("image" , -1);
                Log.d("Demo avatar image",avatarImage+"");



            }



        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (MyProfileFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement MyProfileFragmentListener interface");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public interface MyProfileFragmentListener {
        void goToSelectAvatar();
        void goToDisplayAvatar(User user);
    }
}
