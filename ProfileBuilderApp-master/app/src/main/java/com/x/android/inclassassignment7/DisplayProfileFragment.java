package com.x.android.inclassassignment7;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;


public class DisplayProfileFragment extends Fragment {

    private DisplayProfileFragmentListener mListener;
      User user;
    private TextView name;
    private Button buttonEdit;
    private TextView studentId;
    private TextView departmenttv;
    private ImageView avatarImage;
    public static final String USER = "user";
  //  SharedPreferences sharedPreferences = getSharedPreferences("userInfo" , Context.MODE_PRIVATE);
    SharedPreferences sharedPreferences;// = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
Context context;
    public DisplayProfileFragment( ) {

    }


    @SuppressLint("ValidFragment")
    public DisplayProfileFragment(Context applicationContext) {
        this.context= applicationContext;
        sharedPreferences = this.context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_profile, container, false);

        name = view.findViewById(R.id.textViewNameValue);
        buttonEdit = view.findViewById(R.id.buttonEdit);
        studentId = view.findViewById(R.id.textViewStudentIdValue);
        departmenttv = view.findViewById(R.id.textViewDepatmentValue);
        avatarImage = view.findViewById(R.id.imageViewDisplay);


//        if (!sharedPreferences.getString("firstname","").isEmpty()  ){
//
//
////            user = (User) getArguments().getSerializable(USER);
////            name.setText(user.getFirstName() + " " +user.getLastName());
////            studentId.setText(user.getStudentId());
////            department.setText(user.getDepartment());
////            avatarImage.setImageResource(user.getAvatarImageValue());
//            Gson gson = new Gson();
////            if( gson.fromJson("user",User.class)==null)
////                gson.fromJson("user",User.class);
            Gson gson=new Gson();
            String user_obj_json=sharedPreferences.getString("user", "");
            user=gson.fromJson(user_obj_json, User.class);
            Log.d("Demo user obj display",user+"");


//            String firstname = sharedPreferences.getString("firstname","");
//            String lastname = sharedPreferences.getString("lastname","");
//            String studentID = sharedPreferences.getString("studentID","");
//            String department = sharedPreferences.getString("department","");
//            int image = sharedPreferences.getInt("image",-1);
//
//            user = new User(firstname,lastname,studentID,image,department);
//            user.setFirstName(firstname);
//
//            user.setLastName(lastname);
//
//            user.setStudentId(studentID);
//
//            user.setDepartment(department);
//            user.setAvatarImageValue(image);



            Log.d("Demo image",user.getAvatarImageValue()+"");
            name.setText(user.getFirstName() + " " +user.getLastName());
            studentId.setText(user.getStudentId());
            departmenttv.setText(user.getDepartment());

            avatarImage.setImageResource(user.getAvatarImageValue());



      //  }

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToProfileScreen();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (DisplayProfileFragment.DisplayProfileFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DisplayProfileFragmentListener interface");
        }
    }


    public interface DisplayProfileFragmentListener {
        void goToProfileScreen();
    }
}
