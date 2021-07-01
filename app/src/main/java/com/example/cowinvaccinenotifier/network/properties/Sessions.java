package com.example.cowinvaccinenotifier.network.properties;

import java.util.List;
import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Sessions {

    @com.squareup.moshi.Json(name = "center_id")
    private Integer centerId;
    @com.squareup.moshi.Json(name = "name")
    private String name;
    @com.squareup.moshi.Json(name = "address")
    private String address;
    @com.squareup.moshi.Json(name = "state_name")
    private String stateName;
    @com.squareup.moshi.Json(name = "district_name")
    private String districtName;
    @com.squareup.moshi.Json(name = "block_name")
    private String blockName;
    @com.squareup.moshi.Json(name = "pincode")
    private Integer pincode;
    @com.squareup.moshi.Json(name = "from")
    private String from;
    @com.squareup.moshi.Json(name = "to")
    private String to;
    @com.squareup.moshi.Json(name = "lat")
    private Integer lat;
    @com.squareup.moshi.Json(name = "long")
    private Integer _long;
    @com.squareup.moshi.Json(name = "fee_type")
    private String feeType;
    @com.squareup.moshi.Json(name = "session_id")
    private String sessionId;
    @com.squareup.moshi.Json(name = "date")
    private String date;
    @com.squareup.moshi.Json(name = "available_capacity")
    private Integer availableCapacity;
    @com.squareup.moshi.Json(name = "available_capacity_dose1")
    private Integer availableCapacityDose1;
    @com.squareup.moshi.Json(name = "available_capacity_dose2")
    private Integer availableCapacityDose2;
    @com.squareup.moshi.Json(name = "fee")
    private String fee;
    @com.squareup.moshi.Json(name = "min_age_limit")
    private Integer minAgeLimit;
    @com.squareup.moshi.Json(name = "max_age_limit")
    private Integer maxAgeLimit;
    @com.squareup.moshi.Json(name = "allow_all_age")
    private Boolean allowAllAge;
    @com.squareup.moshi.Json(name = "vaccine")
    private String vaccine;
    @com.squareup.moshi.Json(name = "slots")
    private List<String> slots = null;

    public Integer getCenterId() {
        return centerId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getStateName() {
        return stateName;
    }


    public String getDistrictName() {
        return districtName;
    }


    public String getBlockName() {
        return blockName;
    }


    public Integer getPincode() {
        return pincode;
    }


    public String getFrom() {
        return from;
    }


    public String getTo() {
        return to;
    }


    public Integer getLat() {
        return lat;
    }

    public Integer getLong() {
        return _long;
    }


    public String getFeeType() {
        return feeType;
    }


    public String getSessionId() {
        return sessionId;
    }


    public String getDate() {
        return date;
    }



    public Integer getAvailableCapacity() {
        return availableCapacity;
    }



    public Integer getAvailableCapacityDose1() {
        return availableCapacityDose1;
    }



    public Integer getAvailableCapacityDose2() {
        return availableCapacityDose2;
    }



    public String getFee() {
        return fee;
    }



    public Integer getMinAgeLimit() {
        return minAgeLimit;
    }



    public Integer getMaxAgeLimit() {
        return maxAgeLimit;
    }



    public Boolean getAllowAllAge() {
        return allowAllAge;
    }



    public String getVaccine() {
        return vaccine;
    }



    public List<String> getSlots() {
        return slots;
    }



}