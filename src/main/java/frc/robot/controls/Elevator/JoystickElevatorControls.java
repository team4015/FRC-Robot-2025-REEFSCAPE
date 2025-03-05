package frc.robot.controls.Elevator;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

//This class will create a new command structure 
public class JoystickElevatorControls extends Command {
    private final Elevator elevator;
    private final XboxController controller;

    public JoystickElevatorControls(Elevator elevator, XboxController controller){
        this.elevator = elevator;
        this.controller = controller;
        addRequirements(elevator); //This prevents conflict with other commands
    }

    @Override
    public void execute(){
        double speed = -controller.getLeftY(); //Inverted for natural human movement
        if(Math.abs(speed) < 0.1){ //Deadband to prevent drift
            speed = 0;
        }

        elevator.setSpeed(speed*0.5); //Scale speed
    }

    @Override
    public void end(boolean interrupt){
        elevator.stop();
    }

    @Override
    public boolean isFinished(){
        return false; //Runs until interrupted
    }

}
