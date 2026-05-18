export class Flux2Data {
  readonly counter: number = 0;
  readonly desc?: string;
}

export interface Flux2State {
  readonly flux2Data: Flux2Data;
}

export const initialState2: Flux2State = {
  flux2Data: {
    counter: 28
  }
};
