package com.j.gharibi.parkingyar.dataModule;

public class Dm_ReserveParking {

    int ParkingId,NumberState,FloorParking;
    String ParkingType,TimeReserve,PricePayment;


    public int getParkingId() {
        return ParkingId;
    }

    public void setParkingId(int parkingId) {
        ParkingId = parkingId;
    }

    public int getNumberState() {
        return NumberState;
    }

    public void setNumberState(int numberState) {
        NumberState = numberState;
    }

    public int getFloorParking() {
        return FloorParking;
    }

    public void setFloorParking(int floorParking) {
        FloorParking = floorParking;
    }

    public String getParkingType() {
        return ParkingType;
    }

    public void setParkingType(String parkingType) {
        ParkingType = parkingType;
    }

    public String getTimeReserve() {
        return TimeReserve;
    }

    public void setTimeReserve(String timeReserve) {
        TimeReserve = timeReserve;
    }

    public String getPricePayment() {
        return PricePayment;
    }

    public void setPricePayment(String pricePayment) {
        PricePayment = pricePayment;
    }
}
