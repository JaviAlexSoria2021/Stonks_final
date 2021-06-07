package com.bryansoria.socialappv4;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatMyViewHolder extends RecyclerView.ViewHolder {

    CircleImageView firstUserProfile,secondtUserProfile;
    TextView firstUserText,secondUserText;
    public ChatMyViewHolder(@NonNull View itemView) {
        super(itemView);

        firstUserProfile = itemView.findViewById(R.id.firstUserProfile);
        secondtUserProfile = itemView.findViewById(R.id.secondtUserProfile);
        firstUserText = itemView.findViewById(R.id.firstUserText);
        secondUserText = itemView.findViewById(R.id.secondUserText);
    }
}
