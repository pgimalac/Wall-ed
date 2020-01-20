import numpy as np
from matplotlib import pyplot as plt
import cv2

test = np.zeros((100,100,3),"uint8")

for i in range(100):
    for j in range(100):
        test[i,j,1] = 255
        test[i,j,2] = 255
        test[i,j,0] = 84/2 + 2*85

img2 = cv2.cvtColor(test, cv2.COLOR_HSV2RGB)
plt.imshow(img2)
plt.show()