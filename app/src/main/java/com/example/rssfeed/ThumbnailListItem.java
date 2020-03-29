package com.example.rssfeed;

public class ThumbnailListItem {
    public String SubjectName;
    public String Link;
    public String Image;
    public ThumbnailListItem(String subjectName, String link, String image) {
        this.SubjectName = subjectName;
        this.Link = link;
        this.Image = image;
    }
}
