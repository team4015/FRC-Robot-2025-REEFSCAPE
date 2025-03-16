package frc.robot.controls;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.Auto.AutonomousCommand;
import frc.robot.commands.Drivetrain.DrivetrainCommand;
import frc.robot.commands.Elevator.ElevatorCommand;
import frc.robot.commands.Shooter.ShooterCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;

//This class will assign the joystick control commands as default for the elevator 
public class BindingControls {
    private final Elevator elevator = new Elevator();
    private final Drivetrain drivetrain = new Drivetrain();
    private final Joystick controller = new Joystick(0); //Subject to change
    private final AutonomousCommand autonomouscommand = new AutonomousCommand(drivetrain);
    private final Shooter shooter = new Shooter();

    public BindingControls(){
        configureBindings(); //Call Method
    }
    
    private void configureBindings(){
        //Set joystick control as the deafult command for the elevator
        elevator.setDefaultCommand(new ElevatorCommand(elevator, controller));
        drivetrain.setDefaultCommand(new DrivetrainCommand(drivetrain, controller));
        shooter.setDefaultCommand(new ShooterCommand(shooter, controller));
    }

    public Command getAutonomousCommand(){
        return autonomouscommand;
    }
}