import React from "react";

export interface ChefState {
  ingredients: string[];
  ref: React.RefObject<HTMLDivElement>;
  updateIngreients: (newIngreients: string[]) => void;
}
