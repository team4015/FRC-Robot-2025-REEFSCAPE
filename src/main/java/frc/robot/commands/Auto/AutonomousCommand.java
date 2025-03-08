package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;

public class AutonomousCommand extends Command{
    private Timer timer;
    private boolean isFinished;
    private Drivetrain drive; 
    private Shooter shoot;
    private Elevator elevator;
    

    public AutonomousCommand(){
        timer = new Timer();
        isFinished = false;
        drive = new Drivetrain();
        shoot = new Shooter();
        elevator = new Elevator();
    }
    
    @Override
    public void initialize(){
        timer.reset();
        timer.start();
    }

    @Override 
    public void execute(){
        double time = timer.get();
        if(time < 1.5){
            drive.moveMotors(0.5, 0);
        }
    }

    @Override
    public boolean isFinished(){
        return isFinished;
    }

    @Override
    public void end(boolean interrupted){
        stopRobot();
        timer.stop();
    }

    private void driveFoward(){


    }

    private void turnRight(){


    }
    private void turningLeft(){


    }

    private void stopRobot(){

    }

    private void Shootershoot(){
        shoot.shoot(50);
    }

    private void Shooterstop(){
        shoot.stop();
    }

    private void Elevatorup(){
        elevator.setSpeed(100);

    }

    private void Elevatorstop(){
        elevator.stop();
    }
}
