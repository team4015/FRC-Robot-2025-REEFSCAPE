package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Shooter;

public class IntakeCommand extends Command{
    private final Shooter shooter;

    public IntakeCommand(Shooter shooterSystem){
        this.shooter = shooterSystem;
        addRequirements(shooterSystem);
    }

    @Override 
    public void execute(){
        shooter.intake();
    }

    @Override
    public void end(boolean interrupt){
        shooter.stop();
    }
}
