import pandas as pd
from sklearn.tree import DecisionTreeClassifier
import joblib


model = joblib.load('mosh/music-recommander.joblib')

predictions = model.predict([[21, 1], [22, 0]])

print('-------predictions')
print(predictions)
