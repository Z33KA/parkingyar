package com.j.gharibi.parkingyar.dataModule;

public class Dm_InfoParking {
    double Longitude, Latitude;
    int Id;
    int Floors;
    int TotalQuantityState;
    int FreeState;
    String ParkingType;
    String ParkingName;
    String TimeWork;
    String NameParkingImage;
    String ParkingTypeOmoomi;

    public static final String PERAID = "پراید";
    public static final String SAMAND = "سمند";
    public static final String LAND_CROSE = "لندکروز";

    public String getParkingTypeOmoomi() {
        return ParkingTypeOmoomi;
    }

    public void setParkingTypeOmoomi(String parkingTypeOmoomi) {
        ParkingTypeOmoomi = parkingTypeOmoomi;
    }


    public int getFreeState() {
        return FreeState;
    }

    public void setFreeState(int freeState) {
        FreeState = freeState;
    }

    public String getNameParkingImage() {
        return NameParkingImage;
    }

    public void setNameParkingImage(String nameParkingImage) {
        NameParkingImage = nameParkingImage;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }



    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public String getParkingType() {
        return ParkingType;
    }

    public void setParkingType(String parkingType) {
        ParkingType = parkingType;
    }

    public String getParkingName() {
        return ParkingName;
    }

    public void setParkingName(String parkingName) {
        ParkingName = parkingName;
    }

    public String getTimeWork() {
        return TimeWork;
    }

    public void setTimeWork(String timeWork) {
        TimeWork = timeWork;
    }

    public int getFloors() {
        return Floors;
    }

    public void setFloors(int floors) {
        Floors = floors;
    }

    public int getTotalQuantityState() {
        return TotalQuantityState;
    }

    public void setTotalQuantityState(int totalQuantityState) {
        TotalQuantityState = totalQuantityState;
    }
}
