import matplotlib.pyplot as plt

file = open("/home/victor/pact32/modules/Reconnaissance de Déchets/ImageNet_PACT/retrain_log.txt", "r")

entropy_string_array = []

for line in file:
    token = "Cross entropy = "
    token_length = len(token)
    entropy_index = line.find(token)
    if entropy_index >= 0:
        entropy_string_array.append(line[entropy_index+token_length:-1])

X = [10*i for i in range(len(entropy_array))]
Y = [float(x) for x in entropy_string_array]

plt.plot(X, Y)
plt.title("Cross entropy evolution")
plt.xlabel("Step")
plt.ylabel("Cross entropy")
plt.show()

file.close()
