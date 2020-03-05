import time
import random

import back_wheels
import front_wheels

class Car():
    ''' Whole car control class '''

    _DEBUG = False

    def __init__(self, debug=False):
        self.back_wheels = back_wheels.Back_Wheels(debug=debug)
        self.front_wheels = front_wheels.Front_Wheels(debug=debug)
        self.debug = debug
        self.camera = None

    #########################
    #    DEBUG FUNCTIONS    #
    #########################

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
            print(self._DEBUG_INFO, "Set debug on")
            print(self._DEBUG_INFO, "Set front wheels debug on")
            self.front_wheels.debug = True
            print(self._DEBUG_INFO, "Set back wheels debug on")
            self.back_wheels.debug = True
        else:
            print(self._DEBUG_INFO, "Set debug off")
            print(self._DEBUG_INFO, "Set front wheels debug off")
            self.front_wheels.debug = False
            print(self._DEBUG_INFO, "Set back wheels debug off")
            self.back_wheels.debug = False

    #########################
    #   GENERAL FUNCTIONS   #
    #########################

    def ready(self):
        ''' Get the front wheels to the ready position. '''
        self._debug_('Turn to "Ready" position')
        self.front_wheels.ready()
        self.back_wheels.ready()

    def calibration(self):
        ''' Get the front wheels to the calibration position. '''
        self._debug_('Turn to "Calibration" position')
        self.front_wheels.calibration()
        self.back_wheels.calibration()

    def cali_ok(self):
        ''' Save the calibration value '''
        self.front_wheels.cali_ok()
        self.back_wheels.cali_ok()

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
        self.back_wheels.stop()

    @property
    def speed(self):
        return self.back_wheels.speed

    @speed.setter
    def speed(self, speed):
        ''' Set moving speeds '''
        self.back_wheels.speed = speed

if __name__ == "__main__":
    delta = 0.1
    car = Car(debug=True)

    try:
        car.turn_straight()
        car.forward()

        for i in range(car.turning_max):
            print("Going right: %i" % i)
            car.speed = 10 + 2 * i
            if i % 5 == 0:
                car.turn(car.front_wheels.straight_angle + i)
            time.sleep(delta)

        for i in range(-car.turning_max + 1, car.turning_max):
            print("Going left: %i" % -i)
            if i % 5 == 0:
                car.turn(car.front_wheels.straight_angle - i)
            time.sleep(delta)

        for i in range(-car.turning_max + 1, 1):
            print("Going right: %i" % i)
            car.turn(car.front_wheels.straight_angle + i)
            car.speed = 10 + -2 * i
            time.sleep(delta)

    except KeyboardInterrupt:
        print("Interrupted !")
    finally:
        print("Done")
        car.turn_straight()
        car.stop()
