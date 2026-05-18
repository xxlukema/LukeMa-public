'''exerciseClass.py
This module contains the class NN_Model, which is used to load a pre-trained
neural network model and make predictions based on input features.
It uses the pickle module to load the model from a file and provides a
predict method to make predictions.
'''

import pickle
import os


class NNModel:

    '''docstring for TrainedModel'''

    def __init__(self):

        path = os.getcwd()+'/model_exercise.pkl'
        with open(path, 'rb') as file:
            self.model = pickle.load(file)

    def predict(self, season, age, childish, trauma, surgical, fevers, alcohol, smoking, sitting):
        '''docstring for predict'''
        X = [[season, age, childish, trauma, surgical,
              fevers, alcohol, smoking, sitting]]
        return self.model.predict(X)
