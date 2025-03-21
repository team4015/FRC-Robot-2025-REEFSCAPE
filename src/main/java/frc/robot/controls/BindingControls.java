package frc.robot.controls;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.Auto.AutonomousCommand;
import frc.robot.commands.Elevator.JoystickElevatorControls;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;

//This class will assign the joystick controll commands as default for the elevator 
public class BindingControls {
    private final Elevator elevator = new Elevator();
    private final XboxController controller = new XboxController(0); //Subject to change
    private final AutonomousCommand autonomouscommand = new AutonomousCommand();

    public BindingControls(){
        configureBindings(); //Call Method
    }
    
    private void configureBindings(){
        //Set joystick control as the deafult command for the elevator
        elevator.setDefaultCommand(new JoystickElevatorControls(elevator, controller));
    }
    public Command getAutonomousCommand(){
        return autonomouscommand;
    }
}
