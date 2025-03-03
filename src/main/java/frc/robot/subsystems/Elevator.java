package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase{
    //Hardware
    private final PWMSparkMax motor;
    private final DigitalInput toplimitswitch;
    private final DigitalInput bottomlimitswitch;


    public Elevator(){
        motor = new PWMSparkMax(4); //subject to change
        motor.setSafetyEnabled(true);

        toplimitswitch = new DigitalInput(1); //subject to change 
        bottomlimitswitch = new DigitalInput(2);//subject to change

    }

    public void setSpeed(double speed){
        //Check is limit switches are tiggered and stop motor accordingly
        if ((speed > 0 && toplimitswitch.get()) || (speed < 0 && bottomlimitswitch.get())){
            motor.set(0); //Stop movement if limit switch is trigger
        }
        else{
            motor.set(speed);
        }
    }

    public void stop(){
        motor.set(0); //Ensure motor is stopped
    }
}
