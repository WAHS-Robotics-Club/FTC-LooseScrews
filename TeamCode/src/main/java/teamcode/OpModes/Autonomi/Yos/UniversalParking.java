package teamcode.OpModes.Autonomi.Yos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import teamcode.Objects.Tool.BananaFruit;
import teamcode.Objects.DriveTrain;
import teamcode.Objects.Intake;


@Autonomous(name ="Lame parking for if the new auto suddenly stops working")
public class UniversalParking extends LinearOpMode {

    DriveTrain driveTrain;
    Intake intake;

    @Override
    public void runOpMode() throws InterruptedException {
        driveTrain = DriveTrain.initDriveTrain(hardwareMap, DcMotor.ZeroPowerBehavior.BRAKE, telemetry);
        intake = Intake.initGrabber(hardwareMap);

        telemetry.addData("IsBusy", driveTrain.isBusy());
        driveTrain.logTelemetry(telemetry, driveTrain);
        telemetry.update();
        driveTrain.resetEncoders();
        BananaFruit gyro = new BananaFruit();
        gyro.runBananaFruit(hardwareMap, telemetry);
        telemetry.update();

        waitForStart(); //ONLY MODIFY STUFF AFTER THIS

        driveTrain.moveForwardsBy(telemetry, 24);


        //STILL REQUIRES TESTING
    }
}