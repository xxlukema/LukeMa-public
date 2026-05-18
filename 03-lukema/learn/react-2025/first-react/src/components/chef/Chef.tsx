import Footer from "@/components/footer/Footer"
import React from "react"
import { ChefHeader } from "./ChefHeader/ChefHeader"
import { ChefIngredient } from "./ChefIngredient/ChefIngredient"
import { ChefRecipe } from "./ChefRecipe/ChefRecipe"
import { ChefState } from "./types/ChefState"
import './Chef.scss'


export const Chef = () => {

  const refRecipeSection = React.useRef<HTMLDivElement>(document.createElement("div"))

  const [chefState, setChefState] = React.useState<ChefState>(
    {
      ingredients: [],
      ref: refRecipeSection,
      updateIngreients: (newIngredients) => {
        setChefState(preChefState => {
          return {
            ...preChefState,
            ingredients: newIngredients,
          } as ChefState
        })
      }
    })

  return (
    <div className='chef-main'>
      <ChefHeader />
      <ChefIngredient {...chefState} />
      <ChefRecipe {...chefState} />
      <Footer></Footer>
    </div>
  )
}
