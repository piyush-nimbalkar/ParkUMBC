package storage;

import model.PermitGroup;

import java.util.ArrayList;
import java.util.List;

public final class Seeds {

    static List<PermitGroup> getPermitGroupData() {
        List<PermitGroup> permits = new ArrayList<PermitGroup>();

        permits.add(new PermitGroup(1, "Commuter Student", "A", "red"));
        permits.add(new PermitGroup(2, "Walker Community Student", "B", "green"));
        permits.add(new PermitGroup(3, "Residential Student (Besides Walker)", "C", "yellow"));
        permits.add(new PermitGroup(4, "Faculty/Staff", "D", "violet"));
        permits.add(new PermitGroup(5, "Gated Faculty/Staff", "E", "violet"));
        permits.add(new PermitGroup(6, "Freshman Resident Student", "F", "orange"));
        permits.add(new PermitGroup(7, "Visitor Parking (Metered Spaces)", "P", "blue"));
        permits.add(new PermitGroup(8, "Event Visitor Parking", "PE", "blue"));
        permits.add(new PermitGroup(9, "Handicap Accessible Parking", "PH", "blue"));
        permits.add(new PermitGroup(10, "Electric Vehicle Charging Station", "EV", "dark_green"));

        return permits;
    }

}
