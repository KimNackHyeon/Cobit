package com.finalproject.cobit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Tourist")
@Getter
@Setter
public class TouristDto {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String name;//관광지 이름
   private String type;//구분
   private String address;//도로명 주소
   private String jibun;//지번
   private String latitude;//위도
   private String longitude;//경도
   private String area; //면적
   private String convenienceFacilities;//공공편의시설정보
   private String accommodation;//숙박시설정보
   private String entertainFacilities;//오락시설정보
   private String cultureFacilities;//문화시설정보
   private String hospitalityFacilities; // 접객시설정보
   private String supportFacilities;//지원시설정보
   private String designatedDate;//지정일자
   private String capacity; //수용인원
   private String parkingAvailable; //주차 가능수
   private String introduction;//관광지소개
   private String tel;//관리기관 전화번호
   private String institutionName;//관리기관명
   private String datetime; //데이터 기준 일자
   private String providerCode;//제공기관 코드
   private String providerName; //제공기관 명
   
   public TouristDto() {
      super();
   }
   public TouristDto(Long id, String name, String type, String address, String jibun, String latitude, String longitude,
         String area, String convenienceFacilities, String accommodation, String entertainFacilities,
         String cultureFacilities, String hospitalityFacilities, String supportFacilities, String designatedDate,
         String capacity, String parkingAvailable, String introduction, String tel, String institutionName,
         String datetime, String providerCode, String providerName) {
      super();
      this.id = id;
      this.name = name;
      this.type = type;
      this.address = address;
      this.jibun = jibun;
      this.latitude = latitude;
      this.longitude = longitude;
      this.area = area;
      this.convenienceFacilities = convenienceFacilities;
      this.accommodation = accommodation;
      this.entertainFacilities = entertainFacilities;
      this.cultureFacilities = cultureFacilities;
      this.hospitalityFacilities = hospitalityFacilities;
      this.supportFacilities = supportFacilities;
      this.designatedDate = designatedDate;
      this.capacity = capacity;
      this.parkingAvailable = parkingAvailable;
      this.introduction = introduction;
      this.tel = tel;
      this.institutionName = institutionName;
      this.datetime = datetime;
      this.providerCode = providerCode;
      this.providerName = providerName;
   }
   public Long getId() {
      return id;
   }
   public void setId(Long id) {
      this.id = id;
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getType() {
      return type;
   }
   public void setType(String type) {
      this.type = type;
   }
   public String getAddress() {
      return address;
   }
   public void setAddress(String address) {
      this.address = address;
   }
   public String getJibun() {
      return jibun;
   }
   public void setJibun(String jibun) {
      this.jibun = jibun;
   }
   public String getLatitude() {
      return latitude;
   }
   public void setLatitude(String latitude) {
      this.latitude = latitude;
   }
   public String getLongitude() {
      return longitude;
   }
   public void setLongitude(String longitude) {
      this.longitude = longitude;
   }
   public String getArea() {
      return area;
   }
   public void setArea(String area) {
      this.area = area;
   }
   public String getConvenienceFacilities() {
      return convenienceFacilities;
   }
   public void setConvenienceFacilities(String convenienceFacilities) {
      this.convenienceFacilities = convenienceFacilities;
   }
   public String getAccommodation() {
      return accommodation;
   }
   public void setAccommodation(String accommodation) {
      this.accommodation = accommodation;
   }
   public String getEntertainFacilities() {
      return entertainFacilities;
   }
   public void setEntertainFacilities(String entertainFacilities) {
      this.entertainFacilities = entertainFacilities;
   }
   public String getCultureFacilities() {
      return cultureFacilities;
   }
   public void setCultureFacilities(String cultureFacilities) {
      this.cultureFacilities = cultureFacilities;
   }
   public String getHospitalityFacilities() {
      return hospitalityFacilities;
   }
   public void setHospitalityFacilities(String hospitalityFacilities) {
      this.hospitalityFacilities = hospitalityFacilities;
   }
   public String getSupportFacilities() {
      return supportFacilities;
   }
   public void setSupportFacilities(String supportFacilities) {
      this.supportFacilities = supportFacilities;
   }
   public String getDesignatedDate() {
      return designatedDate;
   }
   public void setDesignatedDate(String designatedDate) {
      this.designatedDate = designatedDate;
   }
   public String getCapacity() {
      return capacity;
   }
   public void setCapacity(String capacity) {
      this.capacity = capacity;
   }
   public String getParkingAvailable() {
      return parkingAvailable;
   }
   public void setParkingAvailable(String parkingAvailable) {
      this.parkingAvailable = parkingAvailable;
   }
   public String getIntroduction() {
      return introduction;
   }
   public void setIntroduction(String introduction) {
      this.introduction = introduction;
   }
   public String getTel() {
      return tel;
   }
   public void setTel(String tel) {
      this.tel = tel;
   }
   public String getInstitutionName() {
      return institutionName;
   }
   public void setInstitutionName(String institutionName) {
      this.institutionName = institutionName;
   }
   public String getDatetime() {
      return datetime;
   }
   public void setDatetime(String datetime) {
      this.datetime = datetime;
   }
   public String getProviderCode() {
      return providerCode;
   }
   public void setProviderCode(String providerCode) {
      this.providerCode = providerCode;
   }
   public String getProviderName() {
      return providerName;
   }
   public void setProviderName(String providerName) {
      this.providerName = providerName;
   }
   
}