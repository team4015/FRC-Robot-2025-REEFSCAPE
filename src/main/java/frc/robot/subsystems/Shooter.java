package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase{
    private final PWMSparkMax motor;
    private final DigitalInput limitswitch;

    public Shooter(){
        motor = new PWMSparkMax(5); //subject to change
        motor.setSafetyEnabled(true);
        
        limitswitch = new DigitalInput(0); //subject to change

        motor.set(0.1);
    }

    public void shoot(double speed){
        motor.set(speed); 
    }

    public void preventEject(){
        if (limitswitch.get()){
            motor.set(0);
        }
    }

    public void stop(){
        motor.set(0);
    }  
}