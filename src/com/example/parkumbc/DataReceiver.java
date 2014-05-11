package com.example.parkumbc;

import model.ParkingLot;

import java.util.ArrayList;

public interface DataReceiver {

    public void receive(ArrayList<ParkingLot> parkingLots);

}
