package com.example.inclass11;

import java.io.Serializable;
import java.util.Objects;

public class Image {
    public String url;
    public String imageReference;
    public double image_id;

    public double getImage_id() {
        return image_id;
    }

    public void setImage_id(double image_id) {
        this.image_id = image_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return url.equals(image.url) &&
                imageReference.equals(image.imageReference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, imageReference);
    }

    @Override
    public String toString() {
        return "Image{" +
                "url='" + url + '\'' +
                ", imageReference='" + imageReference + '\'' +
                '}';
    }

    public Image(String imageURL , String imageReference) {
        this.url=imageURL;
        this.imageReference = imageReference;
    }
    public Image(String imageURL) {
        this.url=imageURL;
        //this.imageReference = imageReference;
    }

    public Image(){

    }
}
