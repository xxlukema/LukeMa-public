export interface DiceData {
  id: string;
  value: number;
  isSelected: boolean;
  handleDiceSelection: (id: string) => void | undefined;
}
