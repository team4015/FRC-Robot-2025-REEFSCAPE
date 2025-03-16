package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class ShooterCommand extends Command{
    private final Shooter shooter;
    private final Joystick controller;

    public ShooterCommand(Shooter shooter, Joystick controller){
        this.shooter = shooter;
        this.controller = controller;
        addRequirements(shooter);
    }

    @Override
    public void execute(){
        double speed = 1;
        if (controller.getRawButtonPressed(5)){
            shooter.shoot(speed);
        }
        if(controller.getRawButtonPressed(6)){
            shooter.preventEject();
        }
    }

    @Override
    public void end(boolean interrupt){
        shooter.stop();
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}