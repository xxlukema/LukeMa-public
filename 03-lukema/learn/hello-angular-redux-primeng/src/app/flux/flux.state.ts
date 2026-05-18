export class FluxData {
  readonly counter: number = 0;
  readonly desc?: string;
}

export interface FluxState {
  readonly fluxData: FluxData;
}

export const initialState: FluxState = {
  fluxData: {
    counter: 8
  }
};
