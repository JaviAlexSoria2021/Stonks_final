package com.bryansoria.socialappv4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bryansoria.socialappv4.Model.Chat;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    EditText inputSms;
    ImageView btnSend;
    CircleImageView userProfileImageAppbar;
    TextView usernameAppbar,status;

    String OtherUserID;

    DatabaseReference mUserRef,smsRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String OtherUsername,OtherUserProfileImageLink,OtherUserStatus;
    FirebaseRecyclerOptions<Chat>options;
    FirebaseRecyclerAdapter<Chat,ChatMyViewHolder>adapter;
    String myProfileImageLink;
    String URL = "https://fcm.googleapis.com/fcm/send";//usaremos un objeto json para enviar la notificacion
    RequestQueue requestQueue;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar= findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        OtherUserID = getIntent().getStringExtra("OtherUserID");

        requestQueue = Volley.newRequestQueue(this);
        recyclerView = findViewById(R.id.recyclerView);
        inputSms = findViewById(R.id.inputSms);
        btnSend = findViewById(R.id.btnSend);
        userProfileImageAppbar = findViewById(R.id.userProfileImageAppbar);
        usernameAppbar = findViewById(R.id.usernameAppbar);
        status = findViewById(R.id.status);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        smsRef = FirebaseDatabase.getInstance().getReference().child("Message");


        LoadOtherUser();
        LoadProfile();
        
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendSMS();
            }
        });

        LoadSMS();

    }

    private void LoadProfile() {
        //carga la imagen desde la base de datos
        mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    myProfileImageLink = snapshot.child("profileImage").getValue().toString();
                    username = snapshot.child("username").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ChatActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    //CHAT  39
    private void LoadSMS() {
        options = new FirebaseRecyclerOptions.Builder<Chat>().setQuery(smsRef.child(mUser.getUid()).child(OtherUserID),Chat.class).build();
        adapter = new FirebaseRecyclerAdapter<Chat, ChatMyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ChatMyViewHolder holder, int position, @NonNull Chat model) {
                if (model.getUserID().equals(mUser.getUid())){
                    holder.firstUserText.setVisibility(View.GONE);
                    holder.firstUserProfile.setVisibility(View.GONE);
                    holder.secondUserText.setVisibility(View.VISIBLE);
                    holder.secondtUserProfile.setVisibility(View.VISIBLE);

                    holder.secondUserText.setText(model.getSms());
                    Picasso.get().load(myProfileImageLink).into(holder.secondtUserProfile);
                } else {
                    holder.firstUserText.setVisibility(View.VISIBLE);
                    holder.firstUserProfile.setVisibility(View.VISIBLE);
                    holder.secondUserText.setVisibility(View.GONE);
                    holder.secondtUserProfile.setVisibility(View.GONE);

                    holder.firstUserText.setText(model.getSms());
                    Picasso.get().load(OtherUserProfileImageLink).into(holder.firstUserProfile);
                }
            }

            @NonNull
            @Override
            public ChatMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleview_sms,parent,false);

                return new ChatMyViewHolder(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }


    //Revisar para postear mas de una vez 37
    private void SendSMS() {
        String sms = inputSms.getText().toString();
        if (sms.isEmpty()){
            Toast.makeText(this, "Por favor introduzca algo de texto", Toast.LENGTH_SHORT).show();
        } else {
            HashMap hashMap = new HashMap();
            hashMap.put("sms",sms);
            hashMap.put("status","unseen");
            hashMap.put("userID",mUser.getUid());
            smsRef.child(OtherUserID).child(mUser.getUid()).push().updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        smsRef.child(mUser.getUid()).child(OtherUserID).push().updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    //sendNotification(sms);
                                    inputSms.setText(null);
                                    Toast.makeText(ChatActivity.this, "SMS sent", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                }
            });


        }
    }

    //QUITAR SI NO ENTIENDO LA EXPLICACION 42
    /*
    private void sendNotification(String sms) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("to","/topics/"+OtherUserID);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("title", "Message from "+username);
            jsonObject1.put("body",sms);


            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("userID",mUser.getUid());
            jsonObject2.put("type","sms");


            jsonObject.put("notification",jsonObject1);
            jsonObject.put("data",jsonObject2);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String>map = new HashMap<>();
                    map.put("content-type","application/json");
                    map.put("authorization","key=AAAAoz7veQg:APA91bG2w4no53XXganWL_Wm3qwox_dnGQYGxHSybAJnbIO4ESpYVMB0ITDLakwtvvW-cxDwXy4hdWbwLMoXlBF6FbSh2Nyyz5c_8YS2Nwi8o1R3IGfdOHmCEKs1pE9ZaW0yRK7FLKSa");
                    return map;
                }
            };
            requestQueue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    private void LoadOtherUser() {
        mUserRef.child(OtherUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    OtherUsername = snapshot.child("username").getValue().toString();
                    OtherUserProfileImageLink = snapshot.child("profileImage").getValue().toString();
                    OtherUserStatus = snapshot.child("status").getValue().toString();

                    Picasso.get().load(OtherUserProfileImageLink).into(userProfileImageAppbar);
                    usernameAppbar.setText(OtherUsername);
                    status.setText(OtherUserStatus);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}