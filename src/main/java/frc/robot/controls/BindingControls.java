package frc.robot.controls;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.JoystickElevatorControls;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;

//This class will assign the joystick controll commands as default for the elevator 
public class BindingControls {
    //elevator
    private final Elevator elevator = new Elevator();
    private final XboxController controller = new XboxController(0); //Subject to change
    //shooter 
    private static final int INTAKE_BUTTON = 3; //subject to change
    private final Shooter shooter = new Shooter();

    public BindingControls(){
        configureBindings(); //Call Method
    }
    
    private void configureBindings(){
        //Set joystick control as the deafult command for the elevator
        elevator.setDefaultCommand(new JoystickElevatorControls(elevator, controller));

        //activate program when gites the trigger at 50%
        new Trigger(() -> controller.getRawAxis(INTAKE_BUTTON) > 0.5).whileTrue(new IntakeCommand(shooter));
    }
}
