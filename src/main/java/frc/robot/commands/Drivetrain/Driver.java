package frc.robot.commands.Drivetrain;


import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class Driver extends Command{
    private final Drivetrain drivetrain;
    private final XboxController controller;

    public Driver(Drivetrain drivetrain, XboxController controller){
        this.drivetrain = drivetrain;
        this.controller = controller;
        addRequirements(drivetrain);
    }
    
    @Override
    public void execute(){
        double xSpeed = controller.getLeftX(); //Strafing left/right
        double ySpeed = -controller.getLeftY();
        double rotation = controller.getRightX();

        boolean fieldOriented = true;

        drivetrain.drive(xSpeed, ySpeed, rotation, fieldOriented);
    }

    @Override
    public void end(boolean interrupt){
        drivetrain.drive(0, 0, 0, true);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}