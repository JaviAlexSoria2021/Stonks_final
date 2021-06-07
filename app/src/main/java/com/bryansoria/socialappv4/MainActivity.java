package com.bryansoria.socialappv4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bryansoria.socialappv4.InterfazGame.HomeActivity;
import com.bryansoria.socialappv4.Model.Comment;
import com.bryansoria.socialappv4.Model.Posts;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mUserRef,PostRef,LikeRef,CommentRef;
    String profileImageUrlV,usernameV; //valores de la base de datos
    CircleImageView profileImageHeader;
    TextView usernameHeader;
    ImageView addImagePost,sendImagePost;
    EditText inputPostDesc;
    private static final int REQUEST_CODE = 101;
    Uri imageUri;
    ProgressDialog mLoadingBar;
    StorageReference postImageRef;
    FirebaseRecyclerAdapter <Posts,MyViewHolder>adapter;
    FirebaseRecyclerOptions<Posts>options;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Comment>CommentOption;
    FirebaseRecyclerAdapter<Comment,CommentViewHolder>CommentAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        toolbar= findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chat App");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        //Variables para añadir Post 13
        addImagePost=findViewById(R.id.addImagePost);
        sendImagePost=findViewById(R.id.send_post_imageView);//Cuando hacemos clic en este boton buscamos los datos en los campos de entrada y los guardamos en el almacen de firebase
        inputPostDesc=findViewById(R.id.inputAddPost);
        mLoadingBar=new ProgressDialog(this);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mAuth = FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PostRef = FirebaseDatabase.getInstance().getReference().child("Post");
        LikeRef = FirebaseDatabase.getInstance().getReference().child("Likes");
        CommentRef = FirebaseDatabase.getInstance().getReference().child("Comments");
        postImageRef = FirebaseStorage.getInstance().getReference().child("PostImages");

        FirebaseMessaging.getInstance().subscribeToTopic(mUser.getUid());


        drawerLayout= findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navView);


        View view = navigationView.inflateHeaderView(R.layout.drawer_header);
        profileImageHeader=view.findViewById(R.id.profileImage_header);//Inicializamos
        usernameHeader=view.findViewById(R.id.username_header);

        navigationView.setNavigationItemSelectedListener(this);

        //Post
        sendImagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPost();
            }
        });
        addImagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        LoadPost();

        //Barra de navegacion
    }

    private void LoadPost() {
        options = new FirebaseRecyclerOptions.Builder<Posts>().setQuery(PostRef,Posts.class).build();
        adapter = new FirebaseRecyclerAdapter<Posts, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Posts model) {
                String postKey= getRef(position).getKey();
                holder.postDesc.setText(model.getPostDesc());
                String timeAgo = calculateTimeAgo(model.getDatePost());
                holder.timeAgo.setText(timeAgo);
                holder.username.setText(model.getUsername());
                Picasso.get().load(model.getPostImageUrl()).into(holder.postImage);
                Picasso.get().load(model.getUserProfileImageUrl()).into(holder.profileImage);
                holder.countLikes(postKey,mUser.getUid(),LikeRef);
                holder.countComments(postKey,mUser.getUid(),CommentRef);

                holder.likeImage.setOnClickListener(new View.OnClickListener() {

                    //Este metodo controlara los likes del post 17
                    @Override
                    public void onClick(View v) {
                        LikeRef.child(postKey).child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    //Borra el Like
                                    LikeRef.child(postKey).child(mUser.getUid()).removeValue();
                                    holder.likeImage.setColorFilter(Color.GRAY);
                                    notifyDataSetChanged();
                                } else {
                                    //Inserta el Like al post
                                    LikeRef.child(postKey).child(mUser.getUid()).setValue("Like");
                                    holder.likeImage.setColorFilter(Color.GREEN);
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
                holder.commentSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String comment = holder.inputComments.getText().toString();
                        if (comment.isEmpty()){
                            Toast.makeText(MainActivity.this, "Please write something in EditText", Toast.LENGTH_SHORT).show();
                        } else {
                            AddComment(holder,postKey,CommentRef,mUser.getUid(),comment);
                        }
                    }
                });
                LoadComment(postKey);
                holder.postImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,ImageViewActivity.class);
                        intent.putExtra("url",model.getPostImageUrl());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_post,parent,false);
                return new MyViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
    //21
    private void LoadComment(String postKey) {
        MyViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        CommentOption = new FirebaseRecyclerOptions.Builder<Comment>().setQuery(CommentRef.child(postKey),Comment.class).build();
        CommentAdapter = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>(CommentOption) {
            @Override
            protected void onBindViewHolder(@NonNull CommentViewHolder holder, int position, @NonNull Comment model) {
                Picasso.get().load(model.getProfileImageUrl()).into(holder.profileImage);
                holder.username.setText(model.getUsername());
                holder.comment.setText(model.getComment());

            }

            @NonNull
            @Override
            public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_comment,parent,false);
                return new CommentViewHolder(view);
            }
        };
        CommentAdapter.startListening();
        MyViewHolder.recyclerView.setAdapter(CommentAdapter);

    }

    //El usuario solo podra añadir un comentario por post, REVISAR 20 para añadir mas (push method before updateChildren())
    private void AddComment(MyViewHolder holder, String postKey, DatabaseReference commentRef, String uid, String comment) {
        HashMap hashMap = new HashMap();
        hashMap.put("username",usernameV);
        hashMap.put("profileImageUrl", profileImageUrlV);
        hashMap.put("comment",comment);

        commentRef.child(postKey).child(uid).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Comments Added", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    holder.inputComments.setText(null);
                } else {
                    Toast.makeText(MainActivity.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Este metodo calculara el tiempo que ha paso desde la publicacion de algun post en la aplicacion
    private String calculateTimeAgo(String datePost) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        try {
            long time = sdf.parse(datePost).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            return ago+"";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  "";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            imageUri=data.getData();
            addImagePost.setImageURI(imageUri);
        }
    }

    private void AddPost() {
        String postDesc=inputPostDesc.getText().toString();
        if (postDesc.isEmpty() || postDesc.length()<3){
            inputPostDesc.setError("Please write something in post description.");
        }else if (imageUri==null){
            Toast.makeText(this, "Plase select an image", Toast.LENGTH_SHORT).show();
        }else{
            mLoadingBar.setTitle("Adding Post");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            Date date = new Date();
            SimpleDateFormat formatter= new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            String strDate = formatter.format(date);

            postImageRef.child(mUser.getUid()+strDate).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){
                        postImageRef.child(mUser.getUid()+strDate).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) { //Aqui obtenemos el Uri de las imagenes subidas

                                HashMap hashMap = new HashMap();
                                hashMap.put("datePost",strDate);
                                hashMap.put("postImageUrl",uri.toString());
                                hashMap.put("postDesc",postDesc);
                                hashMap.put("userProfileImageUrl", profileImageUrlV);
                                hashMap.put("username",usernameV);
                                PostRef.child(mUser.getUid()+strDate).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {

                                    //Borre metodo onComplete Si algo falla revisar esto VIDEO 13 Y 13A
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()) {
                                            mLoadingBar.dismiss();
                                            Toast.makeText(MainActivity.this, "Post Added", Toast.LENGTH_SHORT).show();
                                            addImagePost.setImageResource(R.drawable.ic_add_post_image);
                                            inputPostDesc.setText("");
                                        } else {
                                            mLoadingBar.dismiss();
                                            Toast.makeText(MainActivity.this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        });
                    } else {
                        mLoadingBar.dismiss();
                        Toast.makeText(MainActivity.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mUser==null){
            SendUsertoLoginActivity();
        }else{
            //User Id
            mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        profileImageUrlV =dataSnapshot.child("profileImage").getValue().toString();
                        usernameV=dataSnapshot.child("username").getValue().toString();
                        Picasso.get().load(profileImageUrlV).into(profileImageHeader);//cargamos la imagen de perfil del usuario desde la base de datos
                        usernameHeader.setText(usernameV);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "Sorry, something going wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void SendUsertoLoginActivity() {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //Funciones del menu desplegable del usuario

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.home:
                //AÑADIR enlace para home de JAVI
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                break;
            case R.id.profile:
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                break;
            /*case R.id.friend:
                startActivity(new Intent(MainActivity.this,FriendActivity.class));
                break;*/
            case R.id.listedUsers:
                startActivity(new Intent(MainActivity.this, ListedUsersActivity.class));
                break;

            case R.id.post:
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                break;
            /*case R.id.chat:
                startActivity(new Intent(MainActivity.this,ChatUsersActivity.class));
                break;*/
            case R.id.logout: //Aqui agregare la funcion de cerrar sesion
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}