package com.j.gharibi.parkingyar.dataModule;

public class Dm_ParkingOmoomiReserve extends Dm_ParkingHashieeiReserve {
    public int getFloorStateReserveNumber() {
        return FloorStateReserveNumber;
    }

    public void setFloorStateReserveNumber(int floorStateReserveNumber) {
        FloorStateReserveNumber = floorStateReserveNumber;
    }

    int FloorStateReserveNumber;
}
