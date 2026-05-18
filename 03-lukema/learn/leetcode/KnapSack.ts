
const profits = [1, 6, 10, 16];
const weights = [1, 2, 3, 5];

const maxWeight = 7;


console.log(weights, profits);

const buildSack = (weights: number[], maxWeight: number, sack: number[] = []) => {
  for (let weight of weights) {
    while (getWeight(sack) < maxWeight - weight) {
      sack.push(weights.indexOf[weight]);
    }
  }
}

const getWeight = (sack: number[]): number => {
  let sum = 0;
  for (let i of sack) {
    sum += weights[i];
  }
  return sum;
}

buildSack(weights, 100);

