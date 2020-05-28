import multiprocessing

class Conva():
    lectr = None
    leds = None

    def __init__(self, lectr, leds):
        self.set(lectr, leds)

    def set(self, lectr, leds):
        self.stop()
        self.lectr = multiprocessing.Process(target=lectr)
        self.leds = multiprocessing.Process(target=leds)
        self.start()

    def start(self):
        if self.lectr is not None:
            self.lectr.start()
        if self.leds is not None:
            self.leds.start()

    def stop(self):
        if self.lectr is not None:
            self.lectr.kill()
            self.lectr = None
        if self.leds is not None:
            self.leds.kill()
            self.leds = None
