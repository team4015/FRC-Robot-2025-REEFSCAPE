package frc.robot.Elevator;

import edu.wpi.first.wpilibj.XboxController;

//This class will assign the joystick controll commands as default for the elevator 
public class BindingControls {
    private final Elevator elevator = new Elevator();
    private final XboxController controller = new XboxController(0); //Subject to change

    public BindingControls(){
        configureBindings(); //Call Method
    }
    
    private void configureBindings(){
        //Set joystick control as the deafult command for the elevator
        elevator.setDefaultCommand(new JoystickElevatorControls(elevator, controller));
    }
}
