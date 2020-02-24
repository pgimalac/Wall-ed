import time
from random import randint
import back_wheels as bw
import front_wheels as fw
from SunFounder_PCA9685 import PCA9685



class Car():
    ''' Whole car control class'''
    
    _DEBUG = False
    
    def __init__(self, debug=False):
        self.back_wheels = bw.Back_Wheels(debug=debug)
        self.front_wheels = fw.Front_Wheels(debug=debug)
        self.debug = debug
        self.camera = None
        
    ######################
    #   DEBUG FONCTION   #
    ######################
    
    _DEBUG_INFO = 'DEBUG "voiture.py":'
    def _debug_(self, messsage):
        if self.debug:
            print(self._DEBUG_INFO, message)
            
    @property
    def debug(self):
        return self._DEBUG
    
    @debug.setter
    def debug(self, debug):
        '''set if debug info shows'''
        if debug in (True, False):
            self._DEBUG = debug
        else: 
            raise ValueError('debug must be "True" (Set debug on) or "False" (Set debug off), not "{0}"'.format(debug))
            
        if self.debug: 
            print(self._DEBUG_INFO, "set debug on")
            print(self._DEBUG_INFO, "Set front wheels debug on")
            self.front_wheels.debug = True
            print(self._DEBUG_INFO, "Set back wheels debug on")
            self.back_wheels.debug = True
        else:
            print(self._DEBUG_INFO, "set debug off")
            print(self._DEBUG_INFO, "Set front wheels debug off")
            self.front_wheels.debug = False
            print(self._DEBUG_INFO, "Set back wheels debug off")
            self.back_wheels.debug = False
            
    ########################
    #   GENERAL FONCTION   #
    ########################

    def ready(self): 
        '''Get the front wheels to the ready position.'''
        self._debug_('Turn to "ready" position')
        self.front_wheels.ready()
        self.back_wheels.ready()

    def calibration(self):
        '''Get the front wheels to th ecalibration position.'''
        self._debug_('Turn to "Calibration" position')
        self.front_wheels.calibration()
        self.back_wheels.calibration()
        
    def cali_ok(self):
        '''seave the calibration value'''
        self.front_wheels.cali_ok()
        self.back_wheels.cali_ok()
                
    ##############################
    #   FRONT WHEELS FONCTIONS   #
    ##############################            
                
    def turn_left(self):
        '''turn front wheels to the left'''
        self.front_wheels.turn_left()
    
    def turn_right(self):
        '''turn front wheels to the right'''
        self.front_wheels.turn_right()
    
    def turn_straight(self):
        '''turn front wheels back straight'''
        self.front_wheels.turn_straight()
    
    def turn(self, angle):
        '''turn front wheels to the given angle'''
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
        
    #############################
    #   BACK WHEELS FUNCTIONS   #
    #############################
        
    def forward(self):
        '''move both wheels forward'''
        self.back_wheels.forward()
    
    def backward(self):
        '''Move both wheels backward'''
        self.back_wheels.backward()
        
    def stop(self):
        '''stop both wheels'''
        self.back_wheels.stop()
        
    @property
    def speed(self):
        return self.back_wheels.speed()
    
    @speed.setter
    def speed(self, speed):
        '''set mobving speed'''
        self.back_wheels.speed = speed
    
if __name__ == '__main__':
    DELAY = 0.1
    pwm = PCA9685.PWM(bus_number = 1)
    pwm.setup()
    car= Car(debug=True)
    
    try:
        car.turn_straight()
        car.forward()
        for i in range(50):
            car.speed = i
            time.sleep(DELAY)
        car.speed = 50
                
        while True:
            
            theta= randint(0,3)
            
            if theta==0:
                print("going right")
                for i in range(car.turning_max):
                    car.turn(car.front_wheels.straight_angle + i)
                    time.sleep(DELAY)
                for i in range(car.turning_max):
                    car.turn(car.front_wheels.straight_angle + car.turning_max - i)
                    time.sleep(DELAY)
                    
            
            elif theta==1: 
                print("Going left")
                for i in range(car.turning_max):
                    car.turn(car.front_wheels.straight_angle - i)
                    time.sleep(DELAY)
                for i in range(car.turning_max):
                    car.turn(car.front_wheels.straight_angle - car.turning_max + i)
                    time.sleep(DELAY)
                
            elif theta==2:
                print("Going straight")
                car.turn_straight()
                for i in range(car.turning_max):
                    time.sleep(DELAY)
                    
    except KeyboardInterrupt:
        print("Interrupted !")
    finally:
        print("Done")
        car.turn_straight()
        car.stop()

