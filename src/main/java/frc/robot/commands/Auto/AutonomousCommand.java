package frc.robot.commands.Auto;

import java.util.Scanner;

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
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Case scenario: ");
        int caseScenario = input.nextInt();

        System.out.println("Enter levels: ");
        int level = input.nextInt();
        
        double time = timer.get();

        if(caseScenario == 1){

        }
        else if(caseScenario == 2){


        }
        else if(caseScenario == 3){

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
        drive.drive(0, 0.5, 0, false);
    }

    private void turnRight(){
        drive.drive(0, 0, 0.5, isFinished);

    }
    private void turningLeft(){
        drive.drive(0, 0, -0.5, false);

    }

    private void stopRobot(){
        drive.drive(0, 0, 0, false);

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
