package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase{
    //Hardware
    private final PWMSparkMax motor;
    private final DigitalInput toplimitswitch;
    private final DigitalInput bottomlimitswitch;
    private final XboxController controller;

    private final DigitalInput firstlimitswitch;
    private final DigitalInput secondlimitswitch;
    private final DigitalInput thirdlimitswitch;
    private final DigitalInput fourthlimitswitch;

    int levelCounter = 0;

    public Elevator(){
        motor = new PWMSparkMax(4); //subject to change
        motor.setSafetyEnabled(true);

        toplimitswitch = new DigitalInput(1); //subject to change 
        bottomlimitswitch = new DigitalInput(2);//subject to change

        firstlimitswitch = new DigitalInput(0); //subject to change
        secondlimitswitch = new DigitalInput(0); //Subject to change
        thirdlimitswitch = new DigitalInput(1); //Subject to change
        fourthlimitswitch = new DigitalInput(0); //Subject to change

        controller = new XboxController(0); //Subject to change

    }

    public void setSpeed(double speed){
        //Check is limit switches are tiggered and stop motor accordingly
        if ((speed > 0 && toplimitswitch.get()) || (speed < 0 && bottomlimitswitch.get())){
            motor.set(0); //Stop movement if limit switch is trigger
        }
        else{
            motor.set(speed);
        }
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

        switch(levelCounter){
            case 1:
            motor.set(speed);
            if(firstlimitswitch.get()){
                motor.set(0);
            }
            case 2:
            motor.set(speed);
            if(secondlimitswitch.get()){
                motor.set(0);
            }
            case 3:
            motor.set(speed);
            if(thirdlimitswitch.get()){
                motor.set(speed);
            }
            case 4:
            motor.set(speed);
            if(fourthlimitswitch.get()){
                motor.set(speed);
            }

        }
    }

    public void stop(){
        motor.set(0); //Ensure motor is stopped
    }
}
