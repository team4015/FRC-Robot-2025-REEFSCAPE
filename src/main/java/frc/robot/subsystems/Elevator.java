package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase{
    //Hardware
    private final PWMSparkMax motor;
    private final XboxController controller;
    private final DigitalInput levellimitswitch;

    //variables declared for variable counting
    int levelCounter;
    int targetCounter;

    public Elevator(){
        motor = new PWMSparkMax(4); //subject to change
        motor.setSafetyEnabled(true);

        levellimitswitch = new DigitalInput(0);

        controller = new XboxController(0); //Subject to change
    }

    public void setSpeed(double speed){
        //Check joystick and see which button is pressed and moves according to different levels using limit switch
        if(controller.getXButtonPressed()){
            levelCounter = 1;
        }
        if(controller.getYButtonPressed()){
            levelCounter = 2;
        }
        if(controller.getBButton()){
            levelCounter = 3;
        }
        if(controller.getAButton()){
            levelCounter = 4;
        }

        targetCounter = 0;

        while(levelCounter == targetCounter){
            motor.set(speed);
            if(levellimitswitch.get() == true){
                //increment target counter by one when limit switch is pressed
                targetCounter += 1;
            }
            if(levelCounter == targetCounter){
                //set motors to 0 ---> stop motors
                //reset the targetCounter and levelCounter to 0 respectively
                motor.set(0);
                targetCounter = 0;
                levelCounter = 0;
            }
        }
    }

    public void stop(){
        motor.set(0); //Ensure motor is stopped
    }
}
