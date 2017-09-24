package com.example.katarzkubat.newsapp;

 public class News {

    private String nsection;
    private String ntitle;
    private String ndate;
    private String nurl;

    public News (String section, String title, String date, String url) {
    nsection = section;
    ntitle = title;
    ndate = date;
    nurl = url;
 }
 public String getSection() {
 return nsection;
 }

 public String getTitle() {
 return ntitle;
 }

 public String getDate () {
     return ndate;
 }

 public String getUrl() {
     return nurl;
 }

 }
