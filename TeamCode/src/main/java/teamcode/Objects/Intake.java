package teamcode.Objects;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import teamcode.Objects.Tool.Toggle;

public class Intake {
    public CRServo RubaRolla;
    public Servo Pusher;
    public CRServo Vlift;
    public DcMotor Lwheel;
    public DcMotor Rwheel;
    private Toggle toggleRolla;
    public Servo Stopper;
    private Toggle toggleTransferUp;
    private Toggle toggleTransferDown;
    private Toggle toggleStopa;
    private Toggle toggleDoobleLuncher;

    public static Intake initGrabber(HardwareMap hardwareMap) {
        //Creates and hardware maps the grabber element
        Intake intake = new Intake();
        intake.RubaRolla = hardwareMap.crservo.get("RubaRolla");
        intake.Pusher = hardwareMap.servo.get("Pusher");
        intake.Vlift = hardwareMap.crservo.get("Vlift");
        intake.Stopper = hardwareMap.servo.get("Stopper");

        intake.Lwheel = hardwareMap.dcMotor.get("TLflywheel");
        intake.Lwheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.Lwheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intake.Rwheel = hardwareMap.dcMotor.get("TRflywheel");
        intake.Rwheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.Rwheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intake.toggleRolla = new Toggle();
        intake.toggleTransferUp = new Toggle();
        intake.toggleTransferDown = new Toggle();
        intake.toggleDoobleLuncher = new Toggle();
        intake.toggleStopa = new Toggle();


        return intake;
    }

    //Main methods go BELOW HERE!!!!!


    public void rubberbandDoom (Gamepad gamepad) {
        if (gamepad.a) {
            toggleRolla.toggle();
        }

        if (toggleRolla.isToggled()) {
            RubaRolla.setPower(-1);
        } else {
            RubaRolla.setPower(0);
        }

        if (gamepad.b && !(toggleRolla.isToggled())) {
            RubaRolla.setPower(1);
        }
    }

    public void toggleRolla () {
        toggleRolla.toggle();
        checkToggleRolla();
    }

    public void checkToggleRolla (){
        if (toggleRolla.isToggled()) {
            RubaRolla.setPower(-1);
        } else {
            Stopper.setPosition(0);
        }

    }

    public void terrificTransfer (Gamepad gamepad){
        if (gamepad.y) {
            Pusher.setPosition(0.92);
        } else if (gamepad.x) {
            Pusher.setPosition(0.8);
        } else {
            Pusher.setPosition(0.4);
        }
    }

    public void STOPIT (Gamepad gamepad) {
        if (gamepad.left_bumper) {
            toggleStopa.toggle();
        }

        if (toggleStopa.isToggled()){
             Stopper.setPosition(0.30);
        } else {
            Stopper.setPosition(0.93);
        }
    }

    public void singleSTOPIT (Gamepad gamepad) {
        if (gamepad.dpad_left) {
            toggleStopa.toggle();
        }

        if (toggleStopa.isToggled()){
            Stopper.setPosition(0.32);
        } else {
            Stopper.setPosition(0.94);
        }
    }

    public void logStopIt (Telemetry telemetry) {
        if (toggleStopa.isToggled()) {
            telemetry.addLine();
            telemetry.addLine("--!DO!--!NOT!--!INTAKE!--!AN!--!ARTIFACT!--");
        } else {
            telemetry.addLine("");
            telemetry.addLine("Ok you can intake one now");

        }
    }

    public void toggleStopa () {
        toggleStopa.toggle();
        checkToggleStopa();
    }

    public void checkToggleStopa (){
        if (toggleStopa.isToggled()) {
            Stopper.setPosition(0.32);
        } else {
            Stopper.setPosition(0.94);
        }

    }


    public void lunching (Gamepad gamepad) {
        if (gamepad.right_stick_y >= 0.1){
            Vlift.setPower(-gamepad.right_stick_y);
        } else if (gamepad.right_stick_y <= 0.1) {
            Vlift.setPower(-gamepad.right_stick_y);
        } else {
            Vlift.setPower(0);
        }

    }

    public void singleLunching (Gamepad gamepad) {
        if (gamepad.left_trigger >= 0.1 && gamepad.right_trigger >= 0.1) {
            Vlift.setPower(0);
        } else if (gamepad.left_trigger >= 0.1) {
            Vlift.setPower(gamepad.left_trigger);
        } else if (gamepad.right_trigger >= 0.1) {
            Vlift.setPower(-gamepad.right_trigger);
        } else {
            Vlift.setPower(0);
        }

    }

    public void autoLunchup () {
        toggleTransferUp.toggle();

    }

    public void Upstop () {

    }

    public void autoLunchdown () {

    }

    public void luncher (Gamepad gamepad) {
        if(gamepad.right_bumper){
            toggleDoobleLuncher.toggle();
        }

        if (toggleDoobleLuncher.isToggled()){
            Lwheel.setPower(1.0);
            Rwheel.setPower(1.0);
        } else {
            Lwheel.setPower(0);
            Rwheel.setPower(0);
        }
    }

    public void singleLuncher (Gamepad gamepad) {
        if(gamepad.dpad_right){
            toggleDoobleLuncher.toggle();
        }

        if (toggleDoobleLuncher.isToggled()){
            Lwheel.setPower(1.0);
            Rwheel.setPower(1.0);
        } else {
            Lwheel.setPower(0);
            Rwheel.setPower(0);
        }
    }


    public void toggleLuncher () {
        toggleDoobleLuncher.toggle();
        checktoggleLuncher();
    }

    public void checktoggleLuncher () {
        if (toggleDoobleLuncher.isToggled()) {
            Lwheel.setPower(1.0);
            Rwheel.setPower(1.0);
        } else {
            Lwheel.setPower(0);
            Rwheel.setPower(0);
        }
    }


}

























