package com.example.cafeteriaorderingapp.Dto;

import com.google.gson.annotations.SerializedName;

public class Feedback {
    @SerializedName("feedbackId")
    private int feedbackId;

    @SerializedName("comment")
    private String comment;

    public int getFeedbackId() { return feedbackId; }
    public String getComment() { return comment; }
}
