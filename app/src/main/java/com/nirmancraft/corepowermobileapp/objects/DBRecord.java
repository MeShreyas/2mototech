package com.nirmancraft.corepowermobileapp.objects;

/**
 * Created by Shreyas on 7/12/2014.
 */
public class DBRecord {

    private int _id;
    private String title;
    private String image;
    private String body;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
