package teamcode.OpModes.Autonomi.Yos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import teamcode.Objects.BananaFruit;
import teamcode.Objects.DriveTrain;
import teamcode.Objects.Intake;
import teamcode.Objects.Tool.AprilTag;

@Autonomous(name = "It's Taging Time")
public class AprilTagin extends LinearOpMode {

    AprilTag aprilTag = new AprilTag();
    DriveTrain driveTrain;
    Intake intake;

    @Override
    public void runOpMode() throws InterruptedException {
        aprilTag.initAprilTag(hardwareMap, telemetry);

        driveTrain = DriveTrain.initDriveTrain(hardwareMap, DcMotor.ZeroPowerBehavior.BRAKE);
        intake = Intake.initGrabber(hardwareMap);

        telemetry.addData("IsBusy", driveTrain.isBusy());
        driveTrain.logTelemetry(telemetry, driveTrain);
        telemetry.update();
        driveTrain.resetEncoders();
        BananaFruit gyro = new BananaFruit();
        gyro.runBananaFruit(hardwareMap, telemetry);
        telemetry.update();

        waitForStart();
        //MAIN CODE GOES AFTER THIS.
        //update vision pootal
        aprilTag.update(telemetry);

        //move to spot where robot WILL see the tag
        driveTrain.StrafeRightBy(telemetry, 50.912); //This is under assumption I fix the strafing issue with 1/2 Nightmare
        //Scan tag to identify what order we are launching
        AprilTagDetection ObeliskTag = aprilTag.ObeliskScan(); //This is under assumption my april tag coding works
        //Turn so that robot is perpendicular with the lined up artifacts
        driveTrain.turnToHeading(gyro, telemetry, ObeliskTag.ftcPose.pitch); //This is under assumption I know how angles work

        //depending on the Obelisk tag, the robot will strafe a certain distance in order to pick up the correct order of ballz
        if (ObeliskTag.id == 21) { //This is under assumption the previous code executed exactly how I wanted it to

        } else if (ObeliskTag.id == 22) {

        } else if (ObeliskTag.id == 33) {

        }



    }

}
