from matplotlib.pyplot import fill
import pandas as pd
from sklearn.tree import DecisionTreeClassifier
from sklearn import tree


dataset = pd.read_csv('mosh/music.csv')
X = dataset.drop(columns='genre')
y = dataset['genre']

model = DecisionTreeClassifier()
model.fit(X.values, y.values)

tree.export_graphviz(model,
                     out_file='mosh/music-recommander.dot',
                     feature_names=['age', 'gender'],
                     class_names=sorted(y.unique()),
                     label='all',
                     rounded=True,
                     filled=True)

predictions = model.predict([[21, 1], [22, 0]])

print('-------predictions')
print(predictions)
