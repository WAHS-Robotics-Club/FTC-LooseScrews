package teamcode.OpModes.Autonomi.Yos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import teamcode.Objects.BananaFruit;
import teamcode.Objects.DriveTrain;
import teamcode.Objects.Intake;
import teamcode.Objects.Tool.AprilTag;

@Autonomous(name = "TagTester")
public class TagTestin extends OpMode {

    AprilTag aprilTag = new AprilTag();
    DriveTrain driveTrain;
    Intake intake;
         //MAIN CODE GOES AFTER THIS.

    @Override
    public void init() {
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
    }

    @Override
    public void loop() {
        //update vision pootal
        aprilTag.update();

        //First we scan
        AprilTagDetection id24 = aprilTag.getSpecificTag(24);
        //Then we read
        aprilTag.TagTelemetry(id24);

        //LET'S SCANREAD! (wat)
    }
}
