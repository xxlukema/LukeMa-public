import pandas as pd
import numpy as np
import os


data = pd.read_csv("recipe_1/fruits_training_set.csv") # dataset


print("--- data ---:")
print(data)

print("--- data.iloc[3] ---:")
print(data.iloc[3])

print("--- data.iloc[1:3] ---:")
print(data.iloc[1:3])

print("--- data.iloc[:, 1:3] ---:")
print(data.iloc[:, 1:3])

print("--- data ---:")
data
