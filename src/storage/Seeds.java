package storage;

import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import model.ParkingLot;
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

    static List<ParkingLot> getParkingLotData(ArrayList<ParkingLot> lots) {
        ArrayList<LatLng> corners_one = new ArrayList<LatLng>();
        corners_one.add(new LatLng(39.254206, -76.708099));
        corners_one.add(new LatLng(39.253762, -76.707342));
        corners_one.add(new LatLng(39.252794, -76.708624));
        corners_one.add(new LatLng(39.253579, -76.709322));
        corners_one.add(new LatLng(39.253961, -76.708517));

        ArrayList<LatLng> corners_two = new ArrayList<LatLng>();
        corners_two.add(new LatLng(39.254626, -76.709129));
        corners_two.add(new LatLng(39.254372, -76.708249));
        corners_two.add(new LatLng(39.254165, -76.708576));
        corners_two.add(new LatLng(39.253683, -76.709456));
        corners_two.add(new LatLng(39.254086, -76.709713));
        corners_two.add(new LatLng(39.254264, -76.709408));

        ArrayList<LatLng> corners_three = new ArrayList<LatLng>();
        corners_three.add(new LatLng(39.254879, -76.707530));
        corners_three.add(new LatLng(39.254559, -76.706495));
        corners_three.add(new LatLng(39.253824, -76.707047));
        corners_three.add(new LatLng(39.254131, -76.707514));
        corners_three.add(new LatLng(39.254356, -76.707991));
        corners_three.add(new LatLng(39.254646, -76.707734));

        ArrayList<LatLng> corners_four = new ArrayList<LatLng>();
        corners_four.add(new LatLng(39.255182, -76.708463));
        corners_four.add(new LatLng(39.254987, -76.707814));
        corners_four.add(new LatLng(39.254796, -76.707830));
        corners_four.add(new LatLng(39.254584, -76.708029));
        corners_four.add(new LatLng(39.254796, -76.708624));

        ArrayList<LatLng> corners_five = new ArrayList<LatLng>();
        corners_five.add(new LatLng(39.257853, -76.708034));
        corners_five.add(new LatLng(39.257741, -76.707686));
        corners_five.add(new LatLng(39.257537, -76.707789));
        corners_five.add(new LatLng(39.257658, -76.708186));

        ArrayList<LatLng> corners_six = new ArrayList<LatLng>();
        corners_six.add(new LatLng(39.257307, -76.710672));
        corners_six.add(new LatLng(39.257170, -76.710372));
        corners_six.add(new LatLng(39.256925, -76.710479));
        corners_six.add(new LatLng(39.256954, -76.710533));
        corners_six.add(new LatLng(39.256933, -76.710586));
        corners_six.add(new LatLng(39.257070, -76.710854));

        ArrayList<LatLng> corners_seven = new ArrayList<LatLng>();
        corners_seven.add(new LatLng(39.257386, -76.715039));
        corners_seven.add(new LatLng(39.257178, -76.714288));
        corners_seven.add(new LatLng(39.255902, -76.714913));
        corners_seven.add(new LatLng(39.256214, -76.716141));
        corners_seven.add(new LatLng(39.256513, -76.715991));
        corners_seven.add(new LatLng(39.256484, -76.715803));
        corners_seven.add(new LatLng(39.256812, -76.715674));
        corners_seven.add(new LatLng(39.256816, -76.715594));
        corners_seven.add(new LatLng(39.256941, -76.715556));
        corners_seven.add(new LatLng(39.257128, -76.715363));
        corners_seven.add(new LatLng(39.257120, -76.715277));
        corners_seven.add(new LatLng(39.257427, -76.715127));

        ArrayList<LatLng> corners_eight = new ArrayList<LatLng>();
        corners_eight.add(new LatLng(39.254740, -76.715232));
        corners_eight.add(new LatLng(39.254777, -76.714980));
        corners_eight.add(new LatLng(39.254711, -76.714647));
        corners_eight.add(new LatLng(39.254175, -76.714942));
        corners_eight.add(new LatLng(39.254354, -76.715409));

        ArrayList<LatLng> corners_nine = new ArrayList<LatLng>();
        corners_nine.add(new LatLng(39.257942, -76.713864));
        corners_nine.add(new LatLng(39.257822, -76.713456));
        corners_nine.add(new LatLng(39.257635, -76.713553));
        corners_nine.add(new LatLng(39.257722, -76.713928));

        ArrayList<LatLng> corners_ten = new ArrayList<LatLng>();
        corners_ten.add(new LatLng(39.256603, -76.708410));
        corners_ten.add(new LatLng(39.256433, -76.707847));
        corners_ten.add(new LatLng(39.256092, -76.707981));
        corners_ten.add(new LatLng(39.256329, -76.708646));

        ArrayList<LatLng> corners_eleven = new ArrayList<LatLng>();
        corners_eleven.add(new LatLng(39.256611, -76.706999));
        corners_eleven.add(new LatLng(39.256487, -76.706988));
        corners_eleven.add(new LatLng(39.256354, -76.706527));
        corners_eleven.add(new LatLng(39.256611, -76.706548));

        ArrayList<LatLng> corners_twelve = new ArrayList<LatLng>();
        corners_twelve.add(new LatLng(39.261410, -76.714359));
        corners_twelve.add(new LatLng(39.260745, -76.713050));
        corners_twelve.add(new LatLng(39.259981, -76.713737));
        corners_twelve.add(new LatLng(39.260762, -76.715046));

        ArrayList<LatLng> corners_thirteen = new ArrayList<LatLng>();
        corners_thirteen.add(new LatLng(39.258186, -76.718608));
        corners_thirteen.add(new LatLng(39.257090, -76.716676));
        corners_thirteen.add(new LatLng(39.256757, -76.716955));
        corners_thirteen.add(new LatLng(39.257572, -76.719165));

        ArrayList<LatLng> corners_fourteen = new ArrayList<LatLng>();
        corners_fourteen.add(new LatLng(39.255149, -76.705409));
        corners_fourteen.add(new LatLng(39.254933, -76.704808));
        corners_fourteen.add(new LatLng(39.254427, -76.705108));
        corners_fourteen.add(new LatLng(39.254651, -76.705720));

        ArrayList<LatLng> corners_fifteen = new ArrayList<LatLng>();
        corners_fifteen.add(new LatLng(39.254618, -76.704357));
        corners_fifteen.add(new LatLng(39.254335, -76.703821));
        corners_fifteen.add(new LatLng(39.254111, -76.703810));
        corners_fifteen.add(new LatLng(39.253828, -76.704078));
        corners_fifteen.add(new LatLng(39.254269, -76.704754));

        ArrayList<LatLng> corners_sixteen = new ArrayList<LatLng>();
        corners_sixteen.add(new LatLng(39.255090, -76.702960));
        corners_sixteen.add(new LatLng(39.254409, -76.702466));
        corners_sixteen.add(new LatLng(39.254716, -76.701715));
        corners_sixteen.add(new LatLng(39.254575, -76.701587));
        corners_sixteen.add(new LatLng(39.254035, -76.702724));
        corners_sixteen.add(new LatLng(39.254899, -76.703368));

        ArrayList<LatLng> corners_seventeen = new ArrayList<LatLng>();
        corners_seventeen.add(new LatLng(39.252478, -76.704491));
        corners_seventeen.add(new LatLng(39.252316, -76.704593));
        corners_seventeen.add(new LatLng(39.252478, -76.704848));
        corners_seventeen.add(new LatLng(39.252088, -76.705245));
        corners_seventeen.add(new LatLng(39.252299, -76.705642));
        corners_seventeen.add(new LatLng(39.252790, -76.705030));

        ArrayList<LatLng> corners_eighteen = new ArrayList<LatLng>();
        corners_eighteen.add(new LatLng(39.253375, -76.706623));
        corners_eighteen.add(new LatLng(39.253105, -76.706119));
        corners_eighteen.add(new LatLng(39.253018, -76.706178));
        corners_eighteen.add(new LatLng(39.252927, -76.706039));
        corners_eighteen.add(new LatLng(39.252544, -76.706403));
        corners_eighteen.add(new LatLng(39.252607, -76.706741));
        corners_eighteen.add(new LatLng(39.252598, -76.706854));
        corners_eighteen.add(new LatLng(39.252731, -76.707310));

        ArrayList<LatLng> corners_nineteen = new ArrayList<LatLng>();
        corners_nineteen.add(new LatLng(39.253525, -76.706484));
        corners_nineteen.add(new LatLng(39.254248, -76.705744));
        corners_nineteen.add(new LatLng(39.254131, -76.705336));
        corners_nineteen.add(new LatLng(39.254015, -76.705180));
        corners_nineteen.add(new LatLng(39.253201, -76.705985));

        ArrayList<LatLng> corners_twenty = new ArrayList<LatLng>();
        corners_twenty.add(new LatLng(39.253961, -76.709928));
        corners_twenty.add(new LatLng(39.253222, -76.709322));
        corners_twenty.add(new LatLng(39.253060, -76.709751));
        corners_twenty.add(new LatLng(39.253762, -76.710320));

        ArrayList<LatLng> corners_twentyone = new ArrayList<LatLng>();
        corners_twentyone.add(new LatLng(39.257726, -76.712255));
        corners_twentyone.add(new LatLng(39.257344, -76.711842));
        corners_twentyone.add(new LatLng(39.256850, -76.712555));
        corners_twentyone.add(new LatLng(39.257265, -76.712995));

        ArrayList<LatLng> corners_twentytwo = new ArrayList<LatLng>();
        corners_twentytwo.add(new LatLng(39.252368, -76.713510));
        corners_twentytwo.add(new LatLng(39.251894, -76.711911));
        corners_twentytwo.add(new LatLng(39.251583, -76.712051));
        corners_twentytwo.add(new LatLng(39.252048, -76.713671));

        ArrayList<LatLng> corners_twentythree = new ArrayList<LatLng>();
        corners_twentythree.add(new LatLng(39.236023, -76.712895));
        corners_twentythree.add(new LatLng(39.235973, -76.712509));
        corners_twentythree.add(new LatLng(39.235475, -76.712359));
        corners_twentythree.add(new LatLng(39.235408, -76.712938));
        corners_twentythree.add(new LatLng(39.235890, -76.713067));

        ArrayList<ArrayList<LatLng>> cornersList = new ArrayList<ArrayList<LatLng>>();
        cornersList.add(corners_one);
        cornersList.add(corners_two);
        cornersList.add(corners_three);
        cornersList.add(corners_four);
        cornersList.add(corners_five);
        cornersList.add(corners_six);
        cornersList.add(corners_seven);
        cornersList.add(corners_eight);
        cornersList.add(corners_nine);
        cornersList.add(corners_ten);
        cornersList.add(corners_eleven);
        cornersList.add(corners_twelve);
        cornersList.add(corners_thirteen);
        cornersList.add(corners_fourteen);
        cornersList.add(corners_fifteen);
        cornersList.add(corners_sixteen);
        cornersList.add(corners_seventeen);
        cornersList.add(corners_eighteen);
        cornersList.add(corners_nineteen);
        cornersList.add(corners_twenty);
        cornersList.add(corners_twentyone);
        cornersList.add(corners_twentytwo);
        cornersList.add(corners_twentythree);


        ArrayList<LatLng> entrance = new ArrayList<LatLng>();
        entrance.add(new LatLng(39.253760, -76.709035));
        entrance.add(new LatLng(39.253843, -76.709100));
        entrance.add(new LatLng(39.254574, -76.707748));
        entrance.add(new LatLng(39.254665, -76.707930));
        entrance.add(new LatLng(39.257829, -76.708507));
        entrance.add(new LatLng(39.257094, -76.710851));
        entrance.add(new LatLng(39.256043, -76.715639));
        entrance.add(new LatLng(39.254855, -76.716127));
        entrance.add(new LatLng(39.258066, -76.712541));
        entrance.add(new LatLng(39.256666, -76.707702));
        entrance.add(new LatLng(39.256654, -76.706812));
        entrance.add(new LatLng(39.259451, -76.713100));
        entrance.add(new LatLng(39.256920, -76.718395));
        entrance.add(new LatLng(39.254416, -76.705119));
        entrance.add(new LatLng(39.253743, -76.704175));
        entrance.add(new LatLng(39.254192, -76.702319));
        entrance.add(new LatLng(39.253868, -76.704647));
        entrance.add(new LatLng(39.253635, -76.706804));
        entrance.add(new LatLng(39.253635, -76.706804));
        entrance.add(new LatLng(39.253909, -76.709872));
        entrance.add(new LatLng(39.257510, -76.711592));
        entrance.add(new LatLng(39.252493, -76.713496));
        entrance.add(new LatLng(39.236040, -76.713099));

        for (int i = 0; i < lots.size(); i++) {
            lots.get(i).setCorners(cornersList.get(i));
            lots.get(i).setEntrance(entrance.get(i));
//            parkingLots.add(lots.get(i));
        }

        return lots;
    }

    static long[][] getParkingPermitData() {
        return new long[][]{
                {1, 1}, {1, 4}, {1, 9},
                {2, 5}, {2, 9},
                {3, 1},
                {4, 1}, {4, 9},
                {5, 3}, {5, 9},
                {6, 7}, {6, 9},
                {7, 4}, {7, 5},
                {8, 7}, {8, 9},
                {9, 1},
                {10, 9},
                {11, 3},
                {12, 2},
                {13, 1},
                {14, 1}, {14, 3}, {14, 9},
                {15, 4},
                {16, 1}, {16, 3}, {16, 4}, {16, 5}, {16, 9},
                {17, 9},
                {18, 1}, {18, 8},
                {19, 9}, {19, 10},
                {20, 1}, {20, 7}, {20, 9},
                {21, 3}, {21, 5}, {21, 7},
                {22, 5}, {22, 7}, {22, 9},
                {23, 6}};
    }

}
