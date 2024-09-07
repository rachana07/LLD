import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ElevatorSystem {
    public static void main(String[] args) {
        // Initialize floors
        List<Floor> floors = new ArrayList<>();
        for (int i = 0; i < 10; i++) { // Assuming 10 floors
            floors.add(new Floor(i));
        }

        // Initialize building
        Building building = new Building(floors);

        // Create internal button dispatcher
        InternalButtonDispatcher internalButtonDispatcher = new InternalButtonDispatcher();

        // Create internal buttons and external dispatcher
        InternalButton internalButton = new InternalButton(internalButtonDispatcher);
        ExternalDispatcher externalDispatcher = new ExternalDispatcher();

        // Simulate internal button presses (e.g., inside the elevator)
        System.out.println("Simulating internal button presses:");
        internalButton.pressButton(5);  // Request to floor 5
        internalButton.pressButton(2);  // Request to floor 2

        // Simulate external button presses (e.g., from floors)
        System.out.println("\nSimulating external button presses:");
        externalDispatcher.submitTask(8, ElevatorDirection.UP); // Request elevator at floor 8 going UP
        externalDispatcher.submitTask(3, ElevatorDirection.DOWN); // Request elevator at floor 3 going DOWN

        List<ElevatorController> controllers = ElevatorCreator.elevatorControllerList;
        // Control the elevators
        System.out.println("\nControlling elevators:");
        for (ElevatorController controller : controllers) {
            controller.controlElevator();
        }
    }
}

// Building
// Floor
// ElevatorCar
// ElevatorController -> datastucture to move the element based on the req
// InternalButtons
// ExternalButtons
// InternalButtonDispatcher
// ExternalButtonDispatcher -> algorithm on which lift submits the req
// ElevatorDisplay
// enums -> ElevatorState, ElevatorDirection

enum ElevatorState {
    IDLE,
    MOVING
}

enum ElevatorDirection {
    UP,
    DOWN
}

class Building{
    List<Floor> floor;
    Building(List<Floor> floor){
        this.floor = floor;
    }
}

class Floor{
    int floorId;

    Floor(int floorId){
        this.floorId = floorId;
    }
}

class ElevatorDisplay{
    int floorId;
    ElevatorDirection elevatorDirection;

    public void setDisplay(int floorId, ElevatorDirection elevatorDirection){
        this.floorId = floorId;
        this.elevatorDirection = elevatorDirection;
    }

    public void showDisplay(){
        System.out.println("Floor: " + floorId);
        System.out.println("ElevatorDirection: " + elevatorDirection);
    }
}

class ElevatorCar{

    int id;
    int currFloor;
    ElevatorDirection elevatorDirection;
    ElevatorState elevatorState;
    ElevatorDisplay elevatorDisplay;
    InternalButton internalButton;

    ElevatorCar(int id){
        this.id = id;
        this.currFloor = 0;
        this.elevatorDirection = ElevatorDirection.UP;
        this.elevatorState = ElevatorState.IDLE;
        this.elevatorDisplay = new ElevatorDisplay();
    }

    public boolean moveElevator(int destinationFloor, ElevatorDirection elevatorDirection){
        elevatorState = ElevatorState.MOVING;
        if(elevatorDirection == ElevatorDirection.UP){
            int start = currFloor;
            for(int i=start;i<=destinationFloor;i++){
                currFloor = i;
                elevatorDisplay.setDisplay(currFloor, elevatorDirection);
                elevatorDisplay.showDisplay();
                if(i == destinationFloor){
                    elevatorState = ElevatorState.IDLE;
                    return true;
                }
            }
        }

      if(elevatorDirection == ElevatorDirection.DOWN){
          int start = currFloor;
          for(int i=start;i>=destinationFloor;i--){
              currFloor = i;
              elevatorDisplay.setDisplay(currFloor, elevatorDirection);
              elevatorDisplay.showDisplay();
              if(i == destinationFloor){
                  elevatorState = ElevatorState.IDLE;
                  return true;
              }
          }
      }

        return false;
    }
}

class ElevatorController{
    ElevatorCar elevatorCar;
    PriorityQueue<Integer> upQueue;
    PriorityQueue<Integer> downQueue;

    public ElevatorController(ElevatorCar elevatorCar){
        this.elevatorCar = elevatorCar;
        this.upQueue = new PriorityQueue<>();
        this.downQueue = new PriorityQueue<>((a,b) -> b - a);

    }

    public void submitTask(int floor, ElevatorDirection elevatorDirection){
        if(elevatorDirection == ElevatorDirection.UP){
            upQueue.add(floor);
        }else{
            downQueue.add(floor);
        }
    }

    public void controlElevator(){
        while(!upQueue.isEmpty() || !downQueue.isEmpty()){
            if(elevatorCar.elevatorDirection == ElevatorDirection.UP){
                while(!upQueue.isEmpty()){
                    int nextFloor = upQueue.poll();
                    elevatorCar.moveElevator(nextFloor, ElevatorDirection.UP);
                }
                if (!downQueue.isEmpty()) {
                    elevatorCar.elevatorDirection = ElevatorDirection.DOWN;
                }
            }

            if(elevatorCar.elevatorDirection == ElevatorDirection.DOWN){
                while(!downQueue.isEmpty()){
                    int nextFloor = downQueue.poll();
                    elevatorCar.moveElevator(nextFloor, ElevatorDirection.DOWN);
                }
                if(!upQueue.isEmpty()){
                    elevatorCar.elevatorDirection = ElevatorDirection.UP;
                }
            }
        }

        elevatorCar.elevatorState = ElevatorState.IDLE;
    }
}

class ElevatorCreator {

    static List<ElevatorController> elevatorControllerList = new ArrayList<>();
    static {

        ElevatorCar elevatorCar1 = new ElevatorCar(1);
        ElevatorController controller1 = new ElevatorController(elevatorCar1);

        ElevatorCar elevatorCar2 = new ElevatorCar(2);
        ElevatorController controller2 = new ElevatorController(elevatorCar2);

        elevatorControllerList.add(controller1);
        elevatorControllerList.add(controller2);
    }
}



class InternalButton{

    InternalButtonDispatcher internalButtonDispatcher;

    public InternalButton(InternalButtonDispatcher dispatcher) {
        this.internalButtonDispatcher = dispatcher;
    }

    public void pressButton(int destination) {
        internalButtonDispatcher.submitTask(destination);
    }
}

class InternalButtonDispatcher{
    public List<ElevatorController> elevatorControllers = ElevatorCreator.elevatorControllerList;

    public void submitTask(int floor) {
        for (ElevatorController elevatorController : elevatorControllers) {
            int elevatorID = elevatorController.elevatorCar.id;
            ElevatorDirection elevatorDirection = floor > elevatorController.elevatorCar.currFloor ? ElevatorDirection.UP : ElevatorDirection.DOWN;
            if (elevatorID%2==1 && floor%2==1){
                elevatorController.submitTask(floor,elevatorDirection);
            } else if(elevatorID%2==0 && floor%2==0){
                elevatorController.submitTask(floor,elevatorDirection);

            }
        }
    }
}

class ExternalDispatcher{
    List<ElevatorController> elevatorControllers = ElevatorCreator.elevatorControllerList;

   public void submitTask(int floor, ElevatorDirection elevatorDirection){
       for(ElevatorController elevatorController: elevatorControllers){
           int elevatorID = elevatorController.elevatorCar.id;
           if (elevatorID%2==1 && floor%2==1){
               elevatorController.submitTask(floor,elevatorDirection);
           } else if(elevatorID%2==0 && floor%2==0){
               elevatorController.submitTask(floor,elevatorDirection);

           }

       }
   }

}
