package com.techeasesolution.firebaseblogapp.Model;

public class PostModel {

    String pImage, pTitle, pDescription;

    public PostModel() {
    }

    public PostModel(String pImage, String pTitle, String pDescription) {
        this.pImage = pImage;
        this.pTitle = pTitle;
        this.pDescription = pDescription;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }
}
