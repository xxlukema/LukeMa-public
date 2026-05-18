'''
bankMLPClass.py
This module contains the BankMLP class, which is a multi-layer perceptron (MLP) for binary classification.
'''

import os
import pickle
import pandas as pd


class BankMLP:
    '''
    A class to represent a multi-layer perceptron (MLP) for binary classification.

    Attributes
    ----------
    model : MLPClassifier
        The MLP classifier model.
    model_path : str
        The path to save the model.

    Methods
    -------
    train(X, y)
        Trains the MLP classifier on the provided data.
    predict(X)
        Predicts the class labels for the provided data.
    save_model()
        Saves the trained model to a file.
    load_model()
        Loads a trained model from a file.
    '''

    feature_header = ['age','job','marital','education','default','balance','housing','loan','duration']

    def __init__(self):
        '''
        Initializes the BankMLP class with a MLPClassifier and a model path.
        '''
        with open(os.path.join(os.getcwd(), 'bank_final_model.pkl'), 'rb') as file:
            self.model = pickle.load(file)

    def predict(self, age, job, marital, education, default, balance, housing, loan, duration):
        '''
        Predicts the class labels for the provided data.
        '''

        ##########################################
        # [51, 10, 1, 3, 0, 825, 0, 0, 977],
        # [44, 10, 2, 2, 0, 29, 1, 0, 151]
        ##########################################
        feature_data = [
            [age, job, marital, education, default, balance, housing, loan, duration]
        ]

        data_frame = pd.DataFrame(feature_data, columns=self.feature_header)

        return self.model.predict(data_frame)
