import java.util.ArrayList;
import java.util.List;

public class ParkingLOT {
    public static void main(String[] args) {
        Vehicle vehicle1 = new Vehicle(1, VehicleType.TWO_WHEELER);
        Vehicle vehicle2 = new Vehicle(2, VehicleType.FOUR_WHEELER);

        // Create parking spots
        List<ParkingSpot> twoWheelerSpots = new ArrayList<>();
        twoWheelerSpots.add(new TwoWheelerParkingSpot());
        twoWheelerSpots.add(new TwoWheelerParkingSpot());

        List<ParkingSpot> fourWheelerSpots = new ArrayList<>();
        fourWheelerSpots.add(new FourWheelerParkingSpot());
        fourWheelerSpots.add(new FourWheelerParkingSpot());

        // Create parking managers
        ParkingManager twoWheelerManager = new TwoWheelerParkingManager(twoWheelerSpots);
        ParkingManager fourWheelerManager = new FourWheelerParkingManager(fourWheelerSpots);

        // Entrance and Exit Gates
        EntranceGate entranceGate = new EntranceGate();
        ExitGate exitGate = new ExitGate(new CostComputation());

        // Park vehicles
        entranceGate.processEntry(vehicle1, twoWheelerManager);
        entranceGate.processEntry(vehicle2, fourWheelerManager);

        // Exit vehicles
        exitGate.processExit(vehicle1, twoWheelerManager);
        exitGate.processExit(vehicle2, fourWheelerManager);
    }
}

// parking spot, vehicle, entry gate, exit gate
// parking spot -> two wheeler spot, four wheeler spot => findParkingSpace(),isEmpty(),addVehicle(), removeVehicle()
// parking manager -> manages list of parking spots =>  findParkingSpace(), addVehicle(), removeVehicle(), addParkingSpcae(), removeParkingSpace
// TwoWheelerParkingSpace, FourWheelerParkingSpace
// ParkingStrategy => NearToEntrance(), NeartoElevator()
// Vehicle => Vehicleid, vehicleType
// Ticket => entryTime, Vehicle,parkingSpot
// Entrance Gate => Vehicle, ParkingSpotManager(factory design), ParkingSpotFactory, findSpace(), bookSpot(), getTicket()

enum VehicleType {
    TWO_WHEELER,
    FOUR_WHEELER,
}

class Vehicle {
    int vehicleId;
    VehicleType vehicleType;

    public Vehicle(int vehicleId, VehicleType vehicleType) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
    }
}

class Ticket {
    long entryTime;
    Vehicle vehicle;
    ParkingSpot parkingSpot;

    public Ticket(long entryTime, Vehicle vehicle, ParkingSpot parkingSpot) {
        this.entryTime = entryTime;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
    }

    // Method to generate a new ticket
    public static Ticket generateTicket(Vehicle vehicle, ParkingSpot parkingSpot) {
        long entryTime = System.currentTimeMillis();
        return new Ticket(entryTime, vehicle, parkingSpot);
    }
}

class ParkingSpot {
    boolean isEmpty;
    Vehicle vehicle;

    ParkingSpot() {
        isEmpty = true;
    }

    public void parkVehicle(Vehicle v) {
        if (isEmpty) {
            this.vehicle = v;
            isEmpty = false;
        }
    }

    public void removeVehicle() {
        if (!isEmpty) {
            this.vehicle = null;
            isEmpty = true;
        }
    }


}

class TwoWheelerParkingSpot extends ParkingSpot {

}

class FourWheelerParkingSpot extends ParkingSpot {
}

class ParkingManager {
    List<ParkingSpot> parkingSpots;

    ParkingManager(List<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public ParkingSpot findParkingSpot() {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.isEmpty) {
                return spot;
            }
        }

        return null;
    }


    public void removeVehicle(Vehicle v) {
        for (ParkingSpot spot : parkingSpots) {
            if (spot.vehicle != null && spot.vehicle.vehicleId == v.vehicleId) {
                spot.removeVehicle();
            }
        }
    }
}

class TwoWheelerParkingManager extends ParkingManager {


    TwoWheelerParkingManager(List<ParkingSpot> parkingSpots) {
        super(new ArrayList<>(parkingSpots));
    }
}

class FourWheelerParkingManager extends ParkingManager {
    FourWheelerParkingManager(List<ParkingSpot> parkingSpots) {
        super(new ArrayList<>(parkingSpots));
    }
}


class EntranceGate {
    public Ticket ticket;

    public Ticket processEntry(Vehicle v,ParkingManager parkingManager){
        ParkingSpot parkingSpot = parkingManager.findParkingSpot();
        if (parkingSpot != null) {
            parkingSpot.parkVehicle(v);
            this.ticket = Ticket.generateTicket(v,parkingSpot);
            System.out.println("vehicle " + v.vehicleId + " parked");
            return ticket;
        } else {
            System.out.println("No available parking spot for vehicle " + v.vehicleId);
            return null;
        }

    }
}

class ExitGate {
    CostComputation costComputation;

    public ExitGate(CostComputation costComputation) {
        this.costComputation = costComputation;
    }

    public void processExit(Vehicle v, ParkingManager parkingManager) {
        parkingManager.removeVehicle(v);
        int parkingFee = costComputation.calculateCost();
        System.out.println("Vehicle " + v.vehicleId + " exited. Parking fee: $" + parkingFee);
    }
}

class CostComputation {
    public int calculateCost() {
        // Placeholder for actual cost computation logic
        return 50; // Example fixed rate
    }
}