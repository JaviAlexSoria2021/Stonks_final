package com.bryansoria.socialappv4;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListedUsersViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profileImage;
    TextView username,profession;
    public ListedUsersViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImage = itemView.findViewById(R.id.profileImage);
        username = itemView.findViewById(R.id.username);
        profession = itemView.findViewById(R.id.profession);

    }
}
