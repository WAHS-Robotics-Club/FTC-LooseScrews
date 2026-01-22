package teamcode.Objects;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import teamcode.Objects.Tool.Toggle;

public class Intake {
    public DcMotor RubaRolla;
    public Servo Pusher;
    public CRServo Vlift;
    public DcMotorEx Lwheel;
    public DcMotorEx Rwheel;
    private Toggle toggleRolla;
    public Servo Stopper;
    private Toggle toggleVliftUp;
    private Toggle toggleVliftDown;
    private Toggle toggleAutoStopPush;
    private Toggle toggleStopa;
    private Toggle toggleDoobleLuncher;
    private Toggle toggleLuncherOverride;
    private Toggle toggleLuncherMode1;
    private Toggle toggleLuncherMode2;
    VoltageSensor batteryVoltageSensor;

    public static Intake initGrabber(HardwareMap hardwareMap) {
        //Creates and hardware maps the grabber element
        Intake intake = new Intake();
        intake.RubaRolla = hardwareMap.dcMotor.get("RubaRolla");
        intake.RubaRolla.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.Pusher = hardwareMap.servo.get("Pusher");
        intake.Vlift = hardwareMap.crservo.get("Vlift");
        intake.Stopper = hardwareMap.servo.get("Stopper");

        intake.Lwheel = hardwareMap.get(DcMotorEx.class, "TLflywheel");
        intake.Lwheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.Lwheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intake.Rwheel = hardwareMap.get(DcMotorEx.class, "TRflywheel");
        intake.Rwheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.Rwheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.Rwheel.setDirection(DcMotorSimple.Direction.REVERSE);

        intake.toggleRolla = new Toggle();
        intake.toggleVliftUp = new Toggle();
        intake.toggleVliftDown = new Toggle();
        intake.toggleAutoStopPush = new Toggle();
        intake.toggleDoobleLuncher = new Toggle();
        intake.toggleStopa = new Toggle();
        intake.toggleLuncherOverride = new Toggle();
        intake.toggleLuncherMode1 = new Toggle();
        intake.toggleLuncherMode2 = new Toggle();

        intake.batteryVoltageSensor = hardwareMap.voltageSensor.iterator().next();

        return intake;
    }

    //Main methods go BELOW HERE!!!!!


    public void rubberbandDoom(Gamepad gamepad) {
        if (gamepad.a) {
            toggleRolla.toggle();
        }

        if (toggleRolla.isToggled()) {
            RubaRolla.setPower(1);
        } else {
            RubaRolla.setPower(0);
        }

        if (gamepad.b && !(toggleRolla.isToggled())) {
            RubaRolla.setPower(-1);
        }
    }

    public void toggleRolla() {
        toggleRolla.toggle();
        checkToggleRolla();
    }

    public void checkToggleRolla() {
        if (toggleRolla.isToggled()) {
            RubaRolla.setPower(1);
        } else {
            RubaRolla.setPower(0);
        }

    }

    public void terrificTransfer(Gamepad gamepad) {
        if (gamepad.y) {
            Pusher.setPosition(0.92);
        } else if (gamepad.x) {
            Pusher.setPosition(0.8);
        } else {
            Pusher.setPosition(0.4);
        }
    }

    public void STOPIT(Gamepad gamepad) {
        /* if (gamepad.left_bumper) {
            toggleStopa.toggle();
        }
         */

        if (/*toggleStopa.isToggled()*/ gamepad.left_bumper) {
            Stopper.setPosition(0.4);
        } else {
            Stopper.setPosition(0.93);
        }
    }

    public void singleSTOPIT(Gamepad gamepad) {
        if (gamepad.dpad_left) {
            toggleStopa.toggle();
        }

        if (toggleStopa.isToggled()) {
            Stopper.setPosition(0.32);
        } else {
            Stopper.setPosition(0.94);
        }
    }

    public void logStopIt(Telemetry telemetry) {
        if (toggleStopa.isToggled()) {
            telemetry.addLine();
            telemetry.addLine("--!DO!--!NOT!--!INTAKE!--!AN!--!ARTIFACT!--");
        } else {
            telemetry.addLine("");
            telemetry.addLine("Ok you can intake one now");

        }
    }

    public void StopToggle() {
        toggleStopa.toggle();
        checkToggleStopa();
    }

    public void checkToggleStopa() {
        if (toggleStopa.isToggled()) {
            Stopper.setPosition(0.32);
        } else {
            Stopper.setPosition(0.94);
        }

    }


    public void lunching(Gamepad gamepad) {
        if (gamepad.right_stick_y >= 0.1) {
            Vlift.setPower(-gamepad.right_stick_y);
        } else if (gamepad.right_stick_y <= 0.1) {
            Vlift.setPower(-gamepad.right_stick_y);
        } else {
            Vlift.setPower(0);
        }

    }

    public void singleLunching(Gamepad gamepad) {
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

    public void autoPusha() throws InterruptedException {
        toggleAutoStopPush.toggle();

        if (toggleAutoStopPush.isToggled()) {
            Pusher.setPosition(9.2);
        } else {
            Pusher.setPosition(4);
        }
    }

    public void VliftUp() {
        toggleVliftUp.toggle();

        if (toggleVliftUp.isToggled()) {
            Vlift.setPower(1);
        } else {
            Vlift.setPower(0);
        }
    }

    public void VliftDown() {
        toggleVliftDown.toggle();

        if (toggleVliftDown.isToggled()) {
            Vlift.setPower(-1);
        } else {
            Vlift.setPower(0);
        }
    }

    public void lunchingCycleforAuto() throws InterruptedException {
        StopToggle();
        VliftDown();
        Thread.sleep(1500);
        VliftDown();
        autoPusha();
        Thread.sleep(1000);
        autoPusha();
        Thread.sleep(1000);
        autoPusha();
        Thread.sleep(2000);
        VliftUp();
        Thread.sleep(6000);
    }

    public void lunchingCycleforAuto2() throws InterruptedException {
        StopToggle();
        VliftDown();
        Thread.sleep(1500);
        VliftDown();
        autoPusha();
        Thread.sleep(500);
        autoPusha();
        Thread.sleep(500);
        autoPusha();
        Thread.sleep(1000);
        VliftUp();
        Thread.sleep(4000);
    }

    public void luncher(Gamepad gamepad, double velocitay, Telemetry telemetry) {
        telemetry.addLine();
        if (gamepad.right_bumper) {
            toggleDoobleLuncher.toggle();
        }

        if (gamepad.dpad_up){
            toggleLuncherOverride.toggle();
        }

        double voltage = batteryVoltageSensor.getVoltage();

        if (toggleLuncherOverride.isToggled()) {
            /*
            double maxRPM = velocitay * (voltage / 12);
            telemetry.addLine("CAMERA OVERRIDE IS ACTIVATED");
            if (toggleDoobleLuncher.isToggled()) {
                Lwheel.setVelocity(maxRPM);
                Rwheel.setVelocity(maxRPM);
                telemetry.addLine("YO! current Lwheel Velocity is: " + Lwheel.getVelocity());
                telemetry.addLine("YO! current Rwheel Velocity is: " + Rwheel.getVelocity());
            } else {
                Lwheel.setVelocity(0);
                Rwheel.setVelocity(0);
            }

             */

        } else {

            telemetry.addLine("AYO! THIS IS MANUAL LAUNCH MODE");
            if (toggleDoobleLuncher.isToggled()) {

                double maxRPM = 3100 * (voltage / 12);
                double maxRPMForPositionUno = 2800 * (voltage / 12);
                double maxRPMForPositionDos = 2500 * (voltage / 12);

                if (gamepad.dpad_left) {
                    telemetry.addLine();
                    telemetry.addLine("MORE POWER");
                    Lwheel.setVelocity(maxRPMForPositionUno);
                    Rwheel.setVelocity(maxRPMForPositionUno);
                } else if (gamepad.dpad_right) {
                    telemetry.addLine();
                    telemetry.addLine("EVEN MORE POWER");
                    Lwheel.setVelocity(maxRPM);
                    Rwheel.setVelocity(maxRPM);
                } else {
                    telemetry.addLine();
                    telemetry.addLine("LAUNCHER ON!");
                    Lwheel.setVelocity(maxRPMForPositionDos);
                    Rwheel.setVelocity(maxRPMForPositionDos);
                }
            } else {
                Lwheel.setVelocity(0);
                Rwheel.setVelocity(0);
            }

        }

    }

    public void luncherIfWackyLuncherThatDependsOnConstantlySeeingTheAprilTagDoesntWorkIdeally(Gamepad gamepad) {

    }

    public void oldLuncher(Gamepad gamepad, Telemetry telemetry) {
        if (gamepad.right_bumper) {
            toggleDoobleLuncher.toggle();
        }

        if (toggleDoobleLuncher.isToggled()) {
            Lwheel.setPower(1);
            Rwheel.setPower(1);
        } else {
            Lwheel.setVelocity(0);
            Rwheel.setVelocity(0);
        }
        telemetry.addLine();
        telemetry.addLine("YO! current Lmotor Velocity is: " + Lwheel.getVelocity());
        telemetry.addLine("YO! current Rmotor Velocity is: " + Rwheel.getVelocity());
    }

    public void singleLuncher(Gamepad gamepad) {
        if (gamepad.dpad_right) {
            toggleDoobleLuncher.toggle();
        }

        if (toggleDoobleLuncher.isToggled()) {
            Lwheel.setPower(1.0);
            Rwheel.setPower(1.0);
        } else {
            Lwheel.setPower(0);
            Rwheel.setPower(0);
        }
    }


    public void toggleLuncher() {
        toggleDoobleLuncher.toggle();
        checktoggleLuncher();
    }

    public void toggleVelocitayUno() {
        toggleLuncherMode1.toggle();
    }

    public void toggleVelocitayDos() {
        toggleLuncherMode2.toggle();
    }

    public void checktoggleLuncher() {
        //Only works at full battery
        double voltage = batteryVoltageSensor.getVoltage();
        double maxRPM = 3100 * (voltage / 12);
        double maxRPMForPositionUno = 2500 * (voltage / 12);
        double maxRPMForPositionDos = 2000 * (voltage / 12);

        if (toggleDoobleLuncher.isToggled()) {
            if (toggleLuncherMode1.isToggled()){
                Lwheel.setVelocity(maxRPMForPositionUno);
                Rwheel.setVelocity(maxRPMForPositionUno);
            } else if (toggleLuncherMode2.isToggled()) {
                Lwheel.setVelocity(maxRPMForPositionDos);
                Rwheel.setVelocity(maxRPMForPositionDos);
            } else {
                Lwheel.setVelocity(maxRPM);
                Rwheel.setVelocity(maxRPM);
            }
        } else {
            Lwheel.setPower(0);
            Rwheel.setPower(0);
        }
    }
}





      