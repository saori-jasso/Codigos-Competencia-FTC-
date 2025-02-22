package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name = "York_2024")
public class York_2024 extends LinearOpMode {

    // LEDs
    private RevBlinkinLedDriver leds;

    // Motores
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;

    // Giroscopio
    private IMU giro;

    // Mecanismos
    private Servo Garra;
    private DcMotor mBrazo;

    // Elevador
    private DcMotor elevadorLeft;
    private DcMotor elevadorRight;

    // Variables Elevador Canasta
    private int acum = 0;
    private boolean dpadUpPressedPreviously = false;
    private boolean dpadDownPressedPreviously = false;

    // Variables Elevador Chamber
    private int acum2 = 0;
    private boolean yUpPressedPreviously = false;
    private boolean aDownPressedPreviously = false;

    // Variables Brazo
    private int acum3 = 1;
    private boolean bumperUpPressedPreviously = false;
    private boolean bumperDownPressedPreviously = false;

    // Variables Modos Canasta
    private boolean isCanastaMode = true;
    private boolean modeTogglePressedPreviously = false;

    ///////////////// FUNCIÓN INICIALIZAR /////////////////
    private void inicializar() {
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        // Brakes motores chasis
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Brakes motores elevador
        elevadorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevadorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reset motores elevador
        elevadorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevadorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leds.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_LAVA_PALETTE);
    }

    ///////////////// FUNCIÓN IMU /////////////////
    private double getAngle() {
        Orientation girovalue = giro.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double targetAngle = girovalue.firstAngle;

        // Si se presiona X se resetean los valores del giro
        if (gamepad1.x) {
            giro.resetYaw();
        }
        return targetAngle;
    }

    private void move(double Angle) {
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double z = gamepad1.right_stick_x;
        double den;

        // Pasar a radianes
        Angle = Math.toRadians(Angle);

        // Matriz de rotación
        double[][] matrizR = {{Math.cos(Angle), Math.sin(Angle)}, {-Math.sin(Angle), Math.cos(Angle)}};
        double[] matrizEjesR = {(matrizR[0][0] * x) + (matrizR[0][1] * y), (matrizR[1][0] * x) + (matrizR[1][1] * y)};

        // Denominador
        den = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(z), 1);

        // Power con matriz de rotación
        double frontLeftPower = (matrizEjesR[1] + matrizEjesR[0] + z) / den;
        double backLeftPower = (matrizEjesR[1] - matrizEjesR[0] + z) / den;
        double frontRightPower = (matrizEjesR[1] - matrizEjesR[0] - z) / den;
        double backRightPower = (matrizEjesR[1] + matrizEjesR[0] - z) / den;

        // Set del power con la matriz de rotación incluida
        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
    }

    @Override
    public void runOpMode() {
        leds = hardwareMap.get(RevBlinkinLedDriver.class, "blinkin");

        // Declaración de motores y sensores
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        Garra = hardwareMap.get(Servo.class, "Garra");
        mBrazo = hardwareMap.get(DcMotor.class, "axial");

        // Declaración de giroscopio
        giro = hardwareMap.get(IMU.class, "imu");

        // Declaración de motores elevador
        elevadorRight = hardwareMap.get(DcMotor.class, "elevadorRight");
        elevadorLeft = hardwareMap.get(DcMotor.class, "elevadorLeft");
        elevadorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevadorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        inicializar();
        waitForStart();

        // Reiniciar valores del giroscopio
        giro.resetYaw();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                double a = getAngle();
                move(a);
            }
        }
    }
}
