import pandas as pd
from sklearn.tree import DecisionTreeClassifier


dataset = pd.read_csv('mosh/music.csv')
X = dataset.drop(columns='genre');
y = dataset['genre']

model = DecisionTreeClassifier()
# model.fit(X, y)
model.fit(X.values, y.values)

predictions = model.predict([[21, 1], [22, 0]])

print('-------predictions')
print(predictions)
