package com.airamerica;

public class Coordinate {
	
	private double latitude;
	private double longitude;
	
	public Coordinate(int latDegs, int latMins, int lonDegs, int lonMins) {
		this.latitude = latDegs + latMins/60.0;
		this.longitude = lonDegs + lonMins/60.0;
	}//	public Coordinate(int LATDEGS, int LATMINS, int LONDEGS, int LONMINS)

	public double getLatitude() {
		return latitude;
	}//double getLatitude()

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}// void setLatitude(double latitude)

	public double getLongitude() {
		return longitude;
	}//double getLongitude()

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}//void setLongitude(double longitude)

	
	
	
}//public class Coordinate
