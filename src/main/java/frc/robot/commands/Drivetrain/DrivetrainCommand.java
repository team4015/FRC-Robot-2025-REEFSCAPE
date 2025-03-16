package frc.robot.commands.Drivetrain;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class DrivetrainCommand extends Command{
    private final Drivetrain drivetrain;
    private final Joystick controller;

    public DrivetrainCommand(Drivetrain drivetrain, Joystick controller){
        this.drivetrain = drivetrain;
        this.controller = controller;
        addRequirements(drivetrain);
    }
    
    @Override
    public void execute(){
        double xSpeed = controller.getRawAxis(0); 
        double ySpeed = -controller.getRawAxis(1);
        double zRotation = controller.getRawAxis(4);

        boolean fieldOriented = true;

        drivetrain.drive(xSpeed, ySpeed, zRotation, fieldOriented);
    }

    @Override
    public void end(boolean interrupt){
        drivetrain.drive(0, 0, 0, true);

        drivetrain.resetEncoder();
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}