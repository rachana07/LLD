import com.sun.nio.sctp.AbstractNotificationHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarRentalSystem {

    public static void main(String[] args) {
        List<Vehicle1> vehicles = new ArrayList<>();
        Vehicle1 vehicle1 = new CarVehicle(12, "honda", VehicleType1.CAR,"honda accord", 4, Status.ACTIVE);
        Vehicle1 vehicle2 = new TruckVehicle(13, "ford", VehicleType1.TRUCK, "ford f150", 6, Status.ACTIVE);
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        VehicleManagememtSystem vehicleManagememtSystem = new VehicleManagememtSystem(vehicles);
        Location location = new Location("1234",78728, "Autsin","Texas");
        Store store = new Store(vehicleManagememtSystem,location);
        User user = new User("rach","rachana",213);
        Reservation reservation = store.createReservation(vehicle1,user);
        store.completeReservation(reservation.reservationID);
    }
}

// Vehicle
// User
// Store
// VehicleManagement -> List<Vehicles>
// Location
// Reservation
// Bill
// Payment

enum VehicleType1{
    CAR,
    TRUCK
}

enum Status{
    ACTIVE,
    INACTIVE
}

class Vehicle1 {
    int vehicleId;

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    String vehicleName;
    VehicleType1 vehicleType;
    String modelName;
    int noOfSeat;
    Status status;

    public Vehicle1(int vehicleId, String vehicleName, VehicleType1 vehicleType, String modelName, int noOfSeat, Status status) {
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
        this.modelName = modelName;
        this.noOfSeat = noOfSeat;
        this.status = status;
    }

}



class CarVehicle extends Vehicle1{

    public CarVehicle(int vehicleId, String vehicleName, VehicleType1 vehicleType, String modelName, int noOfSeat, Status status) {
        super(vehicleId, vehicleName, vehicleType, modelName, noOfSeat, status);
    }
}

class TruckVehicle extends Vehicle1{

    public TruckVehicle(int vehicleId, String vehicleName, VehicleType1 vehicleType, String modelName, int noOfSeat, Status status) {
        super(vehicleId, vehicleName, vehicleType, modelName, noOfSeat, status);
    }
}

class VehicleManagememtSystem{
    List<Vehicle1> vehicles;

    VehicleManagememtSystem(List<Vehicle1> vehicles){
        this.vehicles = vehicles;
    }

    public List<Vehicle1> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle1> vehicles) {
        this.vehicles = vehicles;
    }


}

class Store{
    List<Reservation> reservations = new ArrayList<>();
    String storeName;
    VehicleManagememtSystem vehicleManagememtSystem;
    Location location;

    public Store(VehicleManagememtSystem vehicleManagememtSystem, Location location) {
        this.vehicleManagememtSystem = vehicleManagememtSystem;
        this.location = location;
    }

    public void setVehicle(List<Vehicle1> vehicles){
        vehicleManagememtSystem.setVehicles(vehicles);
    }

    public List<Vehicle1> getVehicle(List<Vehicle1> vehicles){
        return vehicleManagememtSystem.getVehicles();
    }

    public Reservation createReservation(Vehicle1 vehicle, User user){
        Reservation reservation = new Reservation(1, user, vehicle, location, ReservationStatus.BOOKED, new Date(), new Date());
        reservations.add(reservation);
        System.out.println("Reservation created for user " + user.getUserName() + " for vehicle " + vehicle.getVehicleName());
        return reservation;
    }

    public boolean completeReservation(int reservationID){
        Reservation reservation = reservations.stream()
                .filter(res -> res.getReservationID() == reservationID)
                .findFirst()
                .orElse(null);
        if (reservation != null) {
            reservations.remove(reservation);
            System.out.println("Reservation with ID " + reservationID + " completed.");
            return true;
        }
        return false;
    }
}

class User{
    String userId;

    public User(String userId, String userName, int drivingLicenseNumber) {
        this.userId = userId;
        this.userName = userName;
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    String userName;
    int drivingLicenseNumber;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(int drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }


}

class Location{
    String address;
    int PINCode;
    String city;
    String state;

    public Location(String address, int PINCode, String city, String state) {
        this.address = address;
        this.PINCode = PINCode;
        this.city = city;
        this.state = state;
    }


}

enum ReservationStatus{
    BOOKED,
    NOT_BOOKED
}

class Reservation{
    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    int reservationID;
   User user;
    Vehicle1 vehicle1;
    Location location;
    ReservationStatus reservationStatus;
    Date fromDate;
    Date toDate;

    public Reservation(int reservationID, User user, Vehicle1 vehicle1, Location location, ReservationStatus reservationStatus, Date fromDate, Date toDate) {
        this.reservationID = reservationID;
        this.user = user;
        this.vehicle1 = vehicle1;
        this.location = location;
        this.reservationStatus = reservationStatus;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

}

class Payment{
    PaymentType paymentType;
    public void payBill(){
        System.out.println("Payment made using " + paymentType);
    }
}

class Bill{
    int paymentId;
    int amountPaid;
    Date paymentDate;
    boolean isRefundable;
    PaymentType paymentType;

    public Bill(int paymentId, int amountPaid, Date paymentDate, boolean isRefundable, PaymentType paymentType) {
        this.paymentId = paymentId;
        this.amountPaid = amountPaid;
        this.paymentDate = paymentDate;
        this.isRefundable = isRefundable;
        this.paymentType = paymentType;
    }


}

enum PaymentType{
    CASH,
    CREDIT_CARD
}