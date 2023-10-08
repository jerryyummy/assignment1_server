package org.example.bean;

public class Album {
    String albumID;
    String image;
    Profile profile;

    public Album(String albumID, String image, Profile profile){
        this.albumID = albumID;
        this.image = image;
        this.profile = profile;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
