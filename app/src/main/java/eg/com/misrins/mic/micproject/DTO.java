package eg.com.misrins.mic.micproject;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by MIC on 27/04/2017.
 */

public class DTO {

    private Integer gov_id,type_id;
    private int image;
    private String name;
    private String degree;
    private String address;
    private String phone;
    private String google_cd;
    private Float distance;
    private LatLng location;

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }




    public  DTO(){}
    public DTO(Integer gov_id,Integer type_id  ,String name,String degree,String address,String phone,String google_cd,int image){
        if(address!=null)
        this.setAddress(address);
        if(degree!=null)this.setDegree(degree);
        if(google_cd!=null)this.setGoogle_cd(google_cd);
        if(gov_id!=null)this.setGov_id(gov_id);
         this.setImage(image);
        if(name!=null)this.setName(name);
        if(phone!=null)this.setPhone(phone);
        if(type_id!=null)this. setType_id(type_id);
    }
    public DTO(Integer type_id,String name){
        this.type_id= type_id;
        this.name = name;


    }
    public DTO(Integer type_id,String name,int image){
        this.type_id= type_id;
        this.name = name;
        this.image = image;

    }
    public Integer getGov_id() {
        return gov_id;
    }

    public Integer getType_id() {
        return type_id;
    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDegree() {
        return degree;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getGoogle_cd() {
        return google_cd;
    }


    public void setGov_id(Integer gov_id) {
         this.gov_id = gov_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public void setImage(int image) {
         this.image = image;
    }

    public void setName(String name) {
         this.name = name;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setAddress(String address) {
         this.address = address;
    }

    public void setPhone(String phone) {
         this.phone = phone;
    }

    public void setGoogle_cd(String google_cd) {
         this.google_cd = google_cd;
    }



}
