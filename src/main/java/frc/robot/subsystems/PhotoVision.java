package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

public class PhotoVision extends TimedRobot {
    //Hardware
    private static final int FrontLeftChannel = 0;
    private static final int RearLeftChannel = 1;
    private static final int FrontRightChannel = 2;
    private static final int RearRightChannel = 3;

    private ADXRS450_Gyro gyro;
    private MecanumDrive robotDrive;
    private final PhotonCamera camera;

    private final PIDController strafeController;
    private final PIDController driveController;
    private final PIDController turnController;

    //CONSTANTS
    private final double targetYaw = 0;
    private final double targetDistance = 1.0;
    private final double targetStrafe = 0;


    public PhotoVision (){
        PWMSparkMax frontLeft = new PWMSparkMax(FrontLeftChannel);
        PWMSparkMax rearLeft = new PWMSparkMax(RearLeftChannel);
        PWMSparkMax frontRight = new PWMSparkMax(FrontRightChannel);
        PWMSparkMax rearRight = new PWMSparkMax(RearRightChannel);

        SendableRegistry.addChild(robotDrive, frontLeft);
        SendableRegistry.addChild(robotDrive, rearLeft);
        SendableRegistry.addChild(robotDrive, frontRight);
        SendableRegistry.addChild(robotDrive, rearRight);
        

        // Invert the right side motors.
        // You may need to change or remove this to match your robot.
        frontRight.setInverted(true);
        rearRight.setInverted(true);

        robotDrive = new MecanumDrive(frontLeft::set, rearLeft::set, frontRight::set, rearRight::set);

        camera = new PhotonCamera("photonvision_camera");
        gyro = new ADXRS450_Gyro();

        //PID controller for alignment

        strafeController = new PIDController(0.03, 0, 0); //Tune values
        driveController = new PIDController(0.05, 0, 0); //Tune values
        turnController = new PIDController(0.03, 0, 0); //Tune values
    }

    @Override
    public void robotInit(){
        gyro.calibrate();
    }

    @Override
    public void teleopPeriodic(){
        var result = camera.getLastestResult(); //Get the latest result from the camera

        if(result.hasTargets()){
            // Get the latest result from the camera
            PhotonTrackedTarget target = result.getBestTarget();
            //Get yaw, distance and strafe offset
            double yaw = target.getYaw();
            double distance = target.getBestCameraToTarget().getTranslation().getX();
            double strafe = target.getBestCameraToTarget().getTranslation().getY();

            //Calculate strafe, forward and rotation speed suing PID controllers
            double strafeSpeed = strafeController.calculate(strafe, targetStrafe);
            double forwardSpeed = driveController.calculate(distance, targetDistance);
            double rotationSpeed = turnController.calculate(gyro.getAngle(), targetYaw); //For gyro rotation

            robotDrive.driveCartesian(strafeSpeed, forwardSpeed, rotationSpeed);

            System.out.println("Yaw: " + yaw + ", Distance: " + distance + ", Strafe: " + strafe);
            System.out.println("Strafe speed: " + strafeSpeed + ", Forward Speed: " + forwardSpeed + ", Rotation Speed: " + rotationSpeed);
        }

        else{
            robotDrive.stopMotor();
            System.out.println("No AprilTag detected");
        }
    }
}
