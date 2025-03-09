package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class ShooterCommand extends Command{
    private final Shooter shooter;
    private final XboxController controller;

    public ShooterCommand(Shooter shooter, XboxController controller){
        this.shooter = shooter;
        this.controller = controller;
        addRequirements(shooter);
    }

    @Override
    public void execute(){
        double speed = controller.getLeftTriggerAxis();

        shooter.shoot(speed);
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
