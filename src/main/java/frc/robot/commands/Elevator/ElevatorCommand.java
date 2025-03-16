package frc.robot.commands.Elevator;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

//This class will create a new command structure 
public class ElevatorCommand extends Command {
    private final Elevator elevator;
    private final Joystick controller;

    public ElevatorCommand(Elevator elevator, Joystick controller){
        this.elevator = elevator;
        this.controller = controller;
        addRequirements(elevator); //This prevents conflict with other commands
    }

    @Override
    public void execute(){
        double speed = -controller.getRawAxis(7); //Inverted for natural human movement
        if(Math.abs(speed) < 0.1){ //Deadband to prevent drift
            speed = 0;
        }

        elevator.setSpeed(speed); //Scale speed

        if(controller.getRawButton(3)){
            elevator.automaticLeveling(speed, 10); //change values
        }
        else if(controller.getRawButton(4)){
            elevator.automaticLeveling(speed, 20); //change values
        }
        else if(controller.getRawButton(1)){
            elevator.automaticLeveling(speed, 30); //change values
        }
        else if(controller.getRawButton(2)){
            elevator.automaticLeveling(speed, 40); //change values
        }
    }

    @Override
    public void end(boolean interrupt){
        elevator.stop();
        elevator.resetEncoder();
    }

    @Override
    public boolean isFinished(){
        //Stop the command if the limit switch is pressed
        double speed = -controller.getRawAxis(7);
        return (speed < 0 && elevator.isBottomLimitPressed()) || (speed > 0 && elevator.isTopLimitPressed());
    }
}
