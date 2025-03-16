package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase{
    //Hardware
    private static final int elevatorMotorChannel = 4;

    private SparkMax motor;
    private RelativeEncoder elevatorEncoder;

    private SparkLimitSwitch reverseSwitch;
    private SparkLimitSwitch forwardSwitch;
    
    public Elevator(){
        motor = new SparkMax(elevatorMotorChannel, MotorType.kBrushless); //subject to change

        elevatorEncoder = motor.getEncoder();

        reverseSwitch = motor.getReverseLimitSwitch();
        forwardSwitch = motor.getForwardLimitSwitch();

    }

    public void setSpeed(double speed){
        if (speed < 0 && reverseSwitch.isPressed()) {
            //If moving down and bottom limit switch is pressed, stop  motor
            motor.set(0);
        }
        else if(speed > 0 && forwardSwitch.isPressed()){
            //If moving up and the top limit switch is pressed, stop the motor
            motor.set(0);
        }
        else{
            //Otherwise, set the motor speed
            motor.set(speed);
        }
    }

    public void automaticLeveling(double speed, double distance){
        resetEncoder();
        setSpeed(speed);
        
        if(getElevatorEncoderDistance() == distance){
            setSpeed(0);
        }
    }

    private double positionConversionFactor(){
        //Configure Encoders
        double spoolCircumference = 0.47878; //Change to the sppol
        double gearRatio = 100; //Example of gear ratio
        double positionConversionFactor  = (spoolCircumference * Math.PI) / gearRatio;

        return positionConversionFactor;
    }

    public double getElevatorEncoderDistance(){
        return elevatorEncoder.getPosition()*positionConversionFactor();
    }

    public void resetEncoder(){
        elevatorEncoder.setPosition(0);
    }

    public void stop(){
        motor.set(0); //Ensure motor is stopped
    }

    public boolean isBottomLimitPressed(){
        return reverseSwitch.isPressed();
    }

    public boolean isTopLimitPressed(){
        return forwardSwitch.isPressed();
    }


    @Override
    public void periodic(){
        SmartDashboard.putBoolean("Status of Bottom Switch", isBottomLimitPressed());
        SmartDashboard.putBoolean("Status of Top Switch", isTopLimitPressed());
        SmartDashboard.putNumber("Elevator Distance", getElevatorEncoderDistance());
    }
}
