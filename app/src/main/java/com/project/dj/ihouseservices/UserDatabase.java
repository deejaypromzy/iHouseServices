package com.project.dj.ihouseservices;

public class UserDatabase {


    private String emailid;
    private String firstName;
    private String lastName;
    private String digitalAdress;
    private String phoneNumber;
    private String photo_url;
    private String role;
    private String approval;
    private String rating;
    private String userType;
    private Integer category;


    private String contract;
    private String contractDate;
    private String contractStatus;
    private String contractRemarks;


    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public UserDatabase(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }


    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public UserDatabase() {

    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getContractString() {
        return contractDate;
    }

    public void setContractString(String contractDate) {
        this.contractDate = contractDate;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getContractRemarks() {
        return contractRemarks;
    }

    public void setContractRemarks(String contractRemarks) {
        this.contractRemarks = contractRemarks;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
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

    public String getDigitalAdress() {
        return digitalAdress;
    }

    public void setDigitalAdress(String digitalAdress) {
        this.digitalAdress = digitalAdress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    UserDatabase(String firstName, String lastName, String phoneNumber, String digitalAdress, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.digitalAdress = digitalAdress;
        this.role = role;

    }
    UserDatabase(String firstName, String lastName, String phoneNumber, String digitalAdress, String role, String userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.digitalAdress = digitalAdress;
        this.role = role;
        this.userType = userType;

    }



    private String proName;
  private String proPrice;
    private String proLocation;
    private String proDesc;

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProPrice() {
        return proPrice;
    }

    public void setProPrice(String proPrice) {
        this.proPrice = proPrice;
    }

    public String getProLocation() {
        return proLocation;
    }

    public void setProLocation(String proLocation) {
        this.proLocation = proLocation;
    }

    public String getProDesc() {
        return proDesc;
    }

    public void setProDesc(String proDesc) {
        this.proDesc = proDesc;
    }
//    public UserDatabase(String proName, String proPrice, String proLocation, String proDesc, String photo_url, String approval, String phoneNumber) {
//        this.proName = proName;
//        this.proPrice = proPrice;
//        this.proLocation = proLocation;
//        this.proDesc = proDesc;
//        this.photo_url = photo_url;
//        this.approval = approval;
//        this.phoneNumber = phoneNumber;
//    }
    public UserDatabase(String proName, String proLocation, String proDesc, String photo_url, String approval,String phoneNumber,String rating,Integer category) {
        this.proName = proName;
        this.proLocation = proLocation;
        this.proDesc = proDesc;
        this.photo_url = photo_url;
        this.approval = approval;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.category = category;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }


    public UserDatabase(String contract, String contractDate, String contractStatus, String contractRemarks) {
        this.contract = contract;
        this.contractDate = contractDate;
        this.contractStatus = contractStatus;
        this.contractRemarks = contractRemarks;
    }
}

