package cn.gs.sb_mongo.domain;

public class Tour {
	private String city;
	private int year;
	
	public Tour(String city,int year){
		this.city=city;
		this.year=year;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
}
