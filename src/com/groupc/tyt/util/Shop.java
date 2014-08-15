package com.groupc.tyt.util;

public class Shop {
	 private String thumbnail;
	 private String name;	
	 private String street_address;
	 private double nightly_rate;
	 public Shop(){}
	 
	 public Shop(String thumbnail, String name,String street_address,double nightly_rate) {
	  this.thumbnail = thumbnail;
	  this.name = name;
	  this.street_address = street_address;
	  this.nightly_rate=nightly_rate;
	 }
	 public String getName() {
	  return name;
	 }
	 public void setName(String name){
		 this.name=name;
	 }
	 public String getThumbnail() {
		  return thumbnail;
	}
	public void setThumbnail(String thumbnail){
			 this.thumbnail=thumbnail;
		 }
	 public void setStreet_address(String street_address){
		 this.street_address=street_address;
	 }
	 public String getStreet_address() {
		  return street_address;
	 }
	 public void setNightly_rate(double nightly_rate){
			 this.nightly_rate=nightly_rate;
		 }
	public double getNightly_rate() {
			  return nightly_rate;
	    }
			
}