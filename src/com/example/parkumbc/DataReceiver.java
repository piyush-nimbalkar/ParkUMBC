package com.example.parkumbc;

import model.ParkingLot;

import java.util.ArrayList;

/* An interface to facilitate interaction between an activity
 * and an asynchronous task
 */
public interface DataReceiver {

    public void receive(ArrayList<ParkingLot> parkingLots);

}
