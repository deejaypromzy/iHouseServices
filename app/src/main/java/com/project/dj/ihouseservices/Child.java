/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.project.dj.ihouseservices;

import android.content.Context;
import android.content.Intent;

/**
 * Data model for each row of the RecyclerView.
 */
class Child {

    //Member variables representing the title, image and information about the sport
    private  String name;
    private  String price;
    private  String location;
    private  String desc;
    private  String qty;
    private  String imageResource;
    String firstName;
    String lastName;
    String phoneNumber;

String Contract;
String ContractStatus;
String ContractDate;
String ContractRemarks;


    static final String TITLE_KEY = "Title";
    static final String NEWS_KEY = "News";
    static final String DETAIL_KEY = "Detail";
    static final String IMAGE_KEY = "Image Resource";

    public Child(String ContractRemarks) {
        this.ContractRemarks = ContractRemarks;
    }

    public String getContract() {
        return Contract;
    }

    public void setContract(String contract) {
        Contract = contract;
    }

    public String getContractStatus() {
        return ContractStatus;
    }

    public void setContractStatus(String contractStatus) {
        ContractStatus = contractStatus;
    }

    public String getContractDate() {
        return ContractDate;
    }

    public void setContractDate(String contractDate) {
        ContractDate = contractDate;
    }

    public String getContractRemarks() {
        return ContractRemarks;
    }

    public void setContractRemarks(String contractRemarks) {
        ContractRemarks = contractRemarks;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getQty() {
        return qty;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageResource() {
        return imageResource;
    }

    public Child(String name, String price, String location, String desc, String imageResource, String qty, String phoneNumber) {
        this.name = name;
        this.price = price;
        this.location = location;
        this.desc = desc;
        this.imageResource = imageResource;
        this.qty = qty;
        this.phoneNumber = phoneNumber;
    }

    public Child(String contract, String contractStatus, String contractDate, String contractRemarks) {
        Contract = contract;
        ContractStatus = contractStatus;
        ContractDate = contractDate;
        ContractRemarks = contractRemarks;
    }

/**
     * Constructor for the Child class data model
     * @param title The name if the sport.
     * @param info Information about the sport.
     * @param detail Information about the sport.
     * @param news Information about the sport
     * @param imageResource The resource for the sport image
     */
//    Child(String title, String info, String detail, String news, int imageResource) {
//        this.name = name;
//        this.info = info;
//        this.detail = detail;
//        this.news = news;
//        this.imageResource = imageResource;
//    }

    /**
     * Gets the title of the sport
     * @return The title of the sport.

    /**
     * Gets the resource for the image
     * @return The resource for the image
     */


    /**
     * Method for creating  the Detail Intent
     * @param context Application context, used for launching the Intent
     * @param title The title of the current Child
     * @param detail The title of the current Child
     * @param imageResId The image resource associated with the current sport
     * @return The Intent containing the extras about the sport, launches Detail activity
     */
    static Intent starter(Context context, String title,String detail,String news,String desc,  String imageResId) {
        Intent detailIntent = new Intent(context, MainActivity.class);
        detailIntent.putExtra(TITLE_KEY, title);
        detailIntent.putExtra(DETAIL_KEY,detail);
        detailIntent.putExtra(NEWS_KEY,news );
        detailIntent.putExtra(NEWS_KEY,desc );
        detailIntent.putExtra(IMAGE_KEY, imageResId);
        return detailIntent;
    }

}
