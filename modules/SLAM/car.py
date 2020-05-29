import time
import random

from . import back_wheels
from . import front_wheels
from . import camera
from .SunFounder_PCA9685 import PCA9685

class Car():
    ''' Whole car control class '''

    def __init__(self, debug=False):
        pwm = PCA9685.PWM(bus_number=1)
        pwm.setup()
        pwm.frequency = 60

        self.back_wheels = back_wheels.Back_Wheels(debug=debug)
        self.front_wheels = front_wheels.Front_Wheels(debug=debug)
        self.camera = camera.Camera(debug=debug)
        self.debug = debug

    #########################
    #    DEBUG FUNCTIONS    #
    #########################

    _DEBUG = False
    _DEBUG_INFO = 'DEBUG "car.py":'

    def _debug_(self, message):
        if self.debug:
            print(self._DEBUG_INFO, message)

    @property
    def debug(self):
        return self._DEBUG

    @debug.setter
    def debug(self, debug):
        ''' Set if debug information shows '''
        if debug in (True, False):
            self._DEBUG = debug
        else:
            raise ValueError('debug must be "True" (Set debug on) or "False" (Set debug off), not "{0}"'.format(debug))

        if self.debug:
            print(self._DEBUG_INFO, "Set car debug on")
        else:
            print(self._DEBUG_INFO, "Set car debug off")

        self.front_wheels.debug = debug
        self.back_wheels.debug = debug
        self.camera.debug = debug

    #########################
    #   GENERAL FUNCTIONS   #
    #########################

    def ready(self):
        ''' Get the front wheels to the ready position. '''
        self._debug_('Turn to "Ready" position')
        self.front_wheels.ready()
        self.back_wheels.ready()
        self.camera.ready()

    ##########################
    # FRONT WHEELS FUNCTIONS #
    ##########################

    def turn_left(self):
        ''' Turn the front wheels to the left '''
        self.front_wheels.turn_left()

    def turn_straight(self):
        ''' Turn the front wheels back straight '''
        self.front_wheels.turn_straight()

    def turn_right(self):
        ''' Turn the front wheels to the right '''
        self.front_wheels.turn_right()

    def turn(self, angle):
        ''' Turn the front wheels to the given absolute angle '''
        self.front_wheels.turn(angle)

    @property
    def turning_max(self):
        return self.front_wheels.turning_max

    @turning_max.setter
    def turning_max(self, angle):
        self.front_wheels.turning_max = angle

    @property
    def turning_offset(self):
        return self.front_wheels.turning_offset

    @turning_offset.setter
    def turning_offset(self, value):
        self.front_wheels.turning_offset = value

    #########################
    # BACK WHEELS FUNCTIONS #
    #########################

    def forward(self):
        ''' Move both wheels forward '''
        self.back_wheels.forward()

    def backward(self):
        ''' Move both wheels backward '''
        self.back_wheels.backward()

    def stop(self):
        ''' Stop both wheels '''
        self.speed = 0
        self.back_wheels.stop()

    @property
    def speed(self):
        return self.back_wheels.speed

    @speed.setter
    def speed(self, speed):
        ''' Set moving speeds '''
        self.back_wheels.speed = speed

    ###########################
    # CAMERA MOTORS FUNCTIONS #
    ###########################

    def camera_turn_left(self, step=None):
        ''' Control the pan servo to make the camera turning left '''
        if step is None:
            self.camera.turn_left()
        else:
            self.camera.turn_left(step=step)

    def camera_turn_right(self, step=None):
        ''' Control the pan servo to make the camera turning right '''
        if step is None:
            self.camera.turn_right()
        else:
            self.camera.turn_right(step=step)

    def camera_turn_up(self, step=None):
        ''' Control the tilt servo to make the camera turning up '''
        if step is None:
            self.camera.turn_up()
        else:
            self.camera.turn_up(step=step)

    def camera_turn_down(self, step=None):
        '''Control the tilt servo to make the camera turning down'''
        if step is None:
            self.camera.turn_down()
        else:
            self.camera.turn_down(step=step)

    def camera_to_position(self, expect_pan, expect_tilt, delay=None):
        '''Control two servo to write the camera to ready position'''
        if delay is None:
            self.camera.to_position(expect_pan, expect_tilt)
        else:
            self.camera.to_position(expect_pan, expect_tilt, delay=delay)

    ###########################
    # CAMERA WEBCAM FUNCTIONS #
    ###########################

    @property
    def webcam(self):
        return self.camera.webcam

    def webcam_setup(self):
        return self.camera.webcam_setup()

    def capture(self):
        return self.camera.capture()

    ###########################
    #     OTHER FUNCTIONS     #
    ###########################

    def randomMove(self, delay=5):
        ''' moves randomly for delay secs and then stops, looking front '''
        try:
            direction = random.randint(-45, 45)
            self.turn(90 + direction)
            self.forward()
            self.speed = 75
            self.camera_to_position(90, 90)
            time.sleep(delay)
        finally:
            self.stop()
            self.camera_to_position(90, 90)
            self.turn_straight()

    def goNear(self, width, height, x, y):
        # no idea how to do it if the tilt or the pan aren't at 0 (=> 90)
        # (would need to do some math and we'd like to avoid that)
        assert self.camera.current_tilt == 0 and self.camera.current_pan == 0

        if y > 3 * height / 4:
            return True

        if x < width / 3:
            self.turn(10)
        elif x > 2 * width / 3:
            self.turn(-10)

        self.forward()
        car.speed = 15

        time.sleep(1)

        self.turn_straight()
        self.speed = 0
        self.stop()

        return False

if __name__ == "__main__":
    import cv2

    delta = 0.1
    car = Car(debug=True)

    try:
        car.turn_straight()
        car.forward()
        car.speed = 30

        for i in range(121):
            car.turn((i % 11) - 5)
            car.camera_to_position(85 + (i % 11), 95 - (i % 11))

            if i % 10 == 0:
                img = car.capture()
                if img is None:
                    print("Photo error")
                else:
                    cv2.imwrite("/tmp/cv2%i" % i, img)

    except KeyboardInterrupt:
        print("Interrupted !")
    finally:
        print("Done")
        car.turn_straight()
        car.stop()
