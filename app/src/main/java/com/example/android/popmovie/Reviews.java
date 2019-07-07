package com.example.android.popmovie;

public class Reviews {

    //Initialize
    private String mAuthor;
    private String mContent;

    /**
     * Create a constructor
     * @param author
     * @param content
     */
    public Reviews(String author, String content){
        mAuthor = author;
        mContent = content;
    }

    /** Getters */
    public String getAuthor() {
        return mAuthor;
    }

    public String getContent() {
        return mContent;
    }
}
