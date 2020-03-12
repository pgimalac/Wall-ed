import matplotlib.pyplot as plt

file = open("/home/victor/pact32/modules/Reconnaissance de Déchets/ImageNet_PACT/retrain_log.txt", "r")

entropy_string_array = []

for line in file:
    token = "Cross entropy = "
    token_length = len(token)
    entropy_index = line.find(token)
<<<<<<< HEAD
    if entropy_index >= 0:
=======
    if entropy_index>=0:
>>>>>>> 377707d50c890f9fda4c6305631b25e8e3abe7d6
        entropy_string_array.append(line[entropy_index+token_length:-1])

X = [10*i for i in range(len(entropy_array))]
Y = [float(x) for x in entropy_string_array]

plt.plot(X, Y)
plt.title("Cross entropy evolution")
plt.xlabel("Step")
plt.ylabel("Cross entropy")
plt.show()

<<<<<<< HEAD
file.close()
=======
file.close()
>>>>>>> 377707d50c890f9fda4c6305631b25e8e3abe7d6
