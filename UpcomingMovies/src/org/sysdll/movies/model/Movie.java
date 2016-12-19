package org.sysdll.movies.model;

import java.util.ArrayList;

public class Movie {
	private String title, thumbnailUrl, adult;
	private int id;
	private String year;
	private double rating;
	private ArrayList<String> genre;

	public Movie() {
	}

	public Movie(String name, String thumbnailUrl, String year, double rating,
			ArrayList<String> genre , String adult , int id) {
		this.title = name;
		this.thumbnailUrl = thumbnailUrl;
		this.year = year;
		this.rating = rating;
		this.genre = genre;
		this.adult = adult;
		this.id= id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String  year) {
		this.year = year;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public ArrayList<String> getGenre() {
		return genre;
	}

	public void setGenre(ArrayList<String> genre) {
		this.genre = genre;
	}
	
	public String getAdult() {
		return adult;
	}

	public void setAdult(String adult) {
		this.adult = adult;
	}

}
