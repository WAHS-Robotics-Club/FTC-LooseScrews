package teamcode.OpModes.Autonomi.Yos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import teamcode.Objects.BananaFruit;
import teamcode.Objects.DriveTrain;
import teamcode.Objects.Intake;
import teamcode.Objects.Tool.AprilTag;

@Autonomous(name = "It's Taging Time (Red)")
public class AprilTagin_RED extends LinearOpMode {

    AprilTag aprilTag = new AprilTag();
    DriveTrain driveTrain;
    Intake intake;

    double launchDist = 34;

    @Override
    public void runOpMode() throws InterruptedException {
        aprilTag.initAprilTag(hardwareMap, telemetry);

        driveTrain = DriveTrain.initDriveTrain(hardwareMap, DcMotor.ZeroPowerBehavior.BRAKE, telemetry);
        intake = Intake.initGrabber(hardwareMap);

        telemetry.addData("IsBusy", driveTrain.isBusy());
        driveTrain.logTelemetry(telemetry, driveTrain);
        telemetry.update();
        driveTrain.resetEncoders();
        BananaFruit gyro = new BananaFruit();
        gyro.runBananaFruit(hardwareMap, telemetry);
        telemetry.update();

        waitForStart();//MAIN CODE GOES AFTER THIS.

        //Here's the plan.
        //First we figure out where we are on the field. I want an auto that works no matter where we start
        //To do that we will look for the obelisk. If the robot can see it, we can assume we are starting in the small triangle.
        //If the robot returns null, we can assume we are starting against the goal.

        driveTrain.moveForwardsBy(telemetry, 36); //moves in order to catch obelisk
        Thread.sleep(1000);
        AprilTagDetection obeliskCode = aprilTag.ObeliskScan();
        Thread.sleep(1500);
        aprilTag.STOP();

        if (obeliskCode.metadata == null) {
            //This means we were against the goal. step one: rotate robot to see Obelisk

            driveTrain.turnToHeading(gyro, telemetry, 90);
            //obelisk scan again
            Thread.sleep(1000);
            obeliskCode = aprilTag.ObeliskScan();
            Thread.sleep(1500);
            aprilTag.STOP();

            //


        } else {
            //This means we started in the small triangle. step one: run intake and pick up the correct order of artifacts



        }

        //FAHHHH I need the real deal field in order to figure out these measurements bruh.








    }

}
