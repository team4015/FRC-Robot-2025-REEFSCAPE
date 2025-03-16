package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Drivetrain extends SubsystemBase{
  //Hardware for Motors
  private static final int FrontLeftChannel = 0;
  private static final int RearLeftChannel = 1;
  private static final int FrontRightChannel = 2;
  private static final int RearRightChannel = 3;

  //Variables for gyro and mecanum drive
  private AHRS gyro;
  private MecanumDrive robotDrive;

  //Variables for the Motors
  private SparkMax frontleft;
  private SparkMax rearleft;
  private SparkMax frontright;
  private SparkMax rearright;

  //Variable for Encoders
  private RelativeEncoder frontleftEncoder;
  private RelativeEncoder frontrightEncoder;
  private RelativeEncoder rearleftEncoder;
  private RelativeEncoder rearrightEncoder;


  public Drivetrain(){
    //Initalizing the motors
    frontleft = new SparkMax(FrontLeftChannel, MotorType.kBrushless);
    rearleft = new SparkMax(RearLeftChannel, MotorType.kBrushless);
    frontright = new SparkMax(FrontRightChannel, MotorType.kBrushless);
    rearright = new SparkMax(RearRightChannel, MotorType.kBrushless);

    frontleft.setInverted(false);
    frontright.setInverted(true);
    rearleft.setInverted(false);
    rearright.setInverted(true);

    //Initalizing the Encoders
    frontleftEncoder = frontleft.getEncoder();
    frontrightEncoder = frontright.getEncoder();
    rearrightEncoder = rearright.getEncoder();
    rearleftEncoder = rearleft.getEncoder();

    //Apply configuration
    SendableRegistry.addChild(robotDrive, frontleft);
    SendableRegistry.addChild(robotDrive, rearleft);
    SendableRegistry.addChild(robotDrive, frontright);
    SendableRegistry.addChild(robotDrive, rearright);

    //Initalizing the Gyro 
    gyro = new AHRS(SPI.Port.kMXP);

    robotDrive = new MecanumDrive(frontleft, rearleft, frontright, rearright);
  }

  public void drive(double xSpeed, double ySpeed, double zRotation, boolean fieldOriented){
    xSpeed = applyDeadBand(xSpeed);
    ySpeed = applyDeadBand(ySpeed);
    zRotation = applyDeadBand(zRotation);
    
    if(fieldOriented){
      Rotation2d gyroAngle = Rotation2d.fromDegrees(-gyro.getAngle());

      robotDrive.driveCartesian(xSpeed, ySpeed, zRotation, gyroAngle);
    }

    else{
      robotDrive.driveCartesian(xSpeed, ySpeed, zRotation);
    }
  }

  private double applyDeadBand(double value){
    double deadband = 0.05;

    if(Math.abs(value) < deadband){
      return 0.0;
    }
    else{
      return value;
    }
  }

  public void resetGyro(){
      gyro.reset();
  }

  public double getGyroAngle(){
    return gyro.getAngle();
  }

  private double positionConvertionFactor(){
    //Configure Encoders
    double wheelCircumference = 0.47878; //example of 6 inch wheel
    double gearRatio = 10.71; //Example of gear ratio
    double positionConversionFactor  = (wheelCircumference * Math.PI) / gearRatio;

    return positionConversionFactor;
  }

  public double getFrontLeftEncoderDistance(){
    return frontleftEncoder.getPosition()*positionConvertionFactor();
  }

  public double getFrontRightEncoderDistance(){
    return frontrightEncoder.getPosition()*positionConvertionFactor();
  }

  public double getRearRightEncoderDistance(){
    return rearrightEncoder.getPosition()*positionConvertionFactor();
  }

  public double getRearLeftEncoderDistance(){
    return rearleftEncoder.getPosition()*positionConvertionFactor();
  }

  public void resetEncoder(){
    frontleftEncoder.setPosition(0);
    frontrightEncoder.setPosition(0);
    rearleftEncoder.setPosition(0);
    rearrightEncoder.setPosition(0);
  }

  @Override
  public void periodic(){
    SmartDashboard.putNumber("Front Left Motor: ", getFrontLeftEncoderDistance());
    SmartDashboard.putNumber("Front Right Motor: ", getFrontRightEncoderDistance());
    SmartDashboard.putNumber("Rear Left Motor: ", getRearLeftEncoderDistance());
    SmartDashboard.putNumber("Rear Right Motor", getRearRightEncoderDistance());
  }
}