import pandas as pd
from sklearn.tree import DecisionTreeClassifier
import joblib


dataset = pd.read_csv('mosh/music.csv')
X = dataset.drop(columns='genre');
y = dataset['genre']

model = DecisionTreeClassifier()
model.fit(X.values, y.values)
joblib.dump(model, 'mosh/music-recommander.joblib')
