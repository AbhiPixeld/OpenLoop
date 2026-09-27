package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "OpenLoop Motor Direction Debugger")
public class MotorDirectionDebugger extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private static final double TEST_POWER = 0.25;

    private int selectedMotor = 0;

    @Override
    public void runOpMode() {

        frontLeft =
                hardwareMap.get(DcMotor.class, OpenLoopBackend.FRONT_LEFT_NAME);

        frontRight =
                hardwareMap.get(DcMotor.class, OpenLoopBackend.FRONT_RIGHT_NAME);

        backLeft =
                hardwareMap.get(DcMotor.class, OpenLoopBackend.BACK_LEFT_NAME);

        backRight =
                hardwareMap.get(DcMotor.class, OpenLoopBackend.BACK_RIGHT_NAME);

        frontLeft.setDirection(
                OpenLoopBackend.REVERSE_FRONT_LEFT
                        ? DcMotor.Direction.REVERSE
                        : DcMotor.Direction.FORWARD
        );

        frontRight.setDirection(
                OpenLoopBackend.REVERSE_FRONT_RIGHT
                        ? DcMotor.Direction.REVERSE
                        : DcMotor.Direction.FORWARD
        );

        backLeft.setDirection(
                OpenLoopBackend.REVERSE_BACK_LEFT
                        ? DcMotor.Direction.REVERSE
                        : DcMotor.Direction.FORWARD
        );

        backRight.setDirection(
                OpenLoopBackend.REVERSE_BACK_RIGHT
                        ? DcMotor.Direction.REVERSE
                        : DcMotor.Direction.FORWARD
        );

        stopAll();

        telemetry.addLine("OPENLOOP MOTOR DEBUGGER");
        telemetry.addLine("----------------------");
        telemetry.addLine("LEFT/RIGHT = select motor");
        telemetry.addLine("A = forward");
        telemetry.addLine("B = backward");
        telemetry.addLine("X = stop");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.dpad_left) {
                selectedMotor--;
                if (selectedMotor < 0) selectedMotor = 3;
                sleep(200);
            }

            if (gamepad1.dpad_right) {
                selectedMotor++;
                if (selectedMotor > 3) selectedMotor = 0;
                sleep(200);
            }

            if (gamepad1.a) {
                runSelectedMotor(TEST_POWER);
            }
            else if (gamepad1.b) {
                runSelectedMotor(-TEST_POWER);
            }
            else {
                stopAll();
            }

            telemetry.addData("Motor", getMotorName());
            telemetry.addData("Power", TEST_POWER);
            telemetry.update();
        }

        stopAll();
    }

    private void runSelectedMotor(double power) {

        stopAll();

        switch (selectedMotor) {

            case 0:
                frontLeft.setPower(power);
                break;

            case 1:
                frontRight.setPower(power);
                break;

            case 2:
                backLeft.setPower(power);
                break;

            case 3:
                backRight.setPower(power);
                break;
        }
    }

    private void stopAll() {

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    private String getMotorName() {

        switch (selectedMotor) {

            case 0:
                return OpenLoopBackend.FRONT_LEFT_NAME;

            case 1:
                return OpenLoopBackend.FRONT_RIGHT_NAME;

            case 2:
                return OpenLoopBackend.BACK_LEFT_NAME;

            case 3:
                return OpenLoopBackend.BACK_RIGHT_NAME;

            default:
                return "Unknown";
        }
    }
}