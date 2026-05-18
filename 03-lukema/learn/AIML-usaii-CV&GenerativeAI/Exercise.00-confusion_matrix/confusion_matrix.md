# Confusion Matrix

- FN (right): Miss a real fire alarm. Fail to detect a decease (Miss an acutal diagnosis).
- FP (below): Miss an important email. False fire alarm.

## Precision

    precision = TP / (TP + FP) -------- Virtical ----- How many predicted positive are correct?

## Recall

    recall    = TP / (TP + FN) -------- Horizental --- How actual positives were correctly identified?

## F1 score (balanced)

    F1 = 2 * Precision * recall / (precision + recall)  --- between [0, 1] inclusive, where 1 is the best.

## Regularization

To prevent overfitting.

1. L1: Lasso
2. L2: Ridge

## Bias vs variance

- High bias: underfitting. Model is too simple.
- High variance: overfitting. Model is too complex.

## Bagging vs Boosting

## Batch gradient descent vs Stochastic gradient descent

## Receiver Operating Characteristic (ROC) curve

## Area Under the Curve (AUC)

## Imbalanced classes

## GENS - For language

## CNN - For Image classification (Retina)

## RNN - For time series

## Reinforcement learning (Self-driving cars, gambling, healthcare)

## Large-scale dataset with limited computing resources

- Large dataset requires time and memory
- Limited memory, slow GPU

1. Pretrained models (Transfer learning)
2. Reduce the size of dataset (Data sampling)
3. Batch size adjustment
4. Simplify model architecture (layers, parameters)
5. Gradient accumulation
