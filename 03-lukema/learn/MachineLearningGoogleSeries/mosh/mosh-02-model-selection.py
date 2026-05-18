import pandas as pd
from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score


dataset = pd.read_csv('mosh/music.csv')
X = dataset.drop(columns='genre');
y = dataset['genre']

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)

model = DecisionTreeClassifier()
model.fit(X_train.values, y_train.values)

predictions = model.predict(X_test)

print('-------predictions')
print(predictions)

accuracy = accuracy_score(y_test, predictions)
print('-------accuracy')
print(accuracy)


