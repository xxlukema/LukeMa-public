import { ChefState } from '@/components/chef/types/ChefState'
import './ChefRecipe.scss'
import { recipes } from '@/components/chef/ChefRecipe/RecipeData';
import React from 'react';

export const ChefRecipe = (props: ChefState) => {

  const refP = React.useRef(null)

  React.useEffect(() => {
    console.debug('refP ----', refP)
    console.debug('props.ref ----', props.ref)

    props.ref.current?.scrollIntoView()
  }, [props.ingredients, props.ref])

  console.debug('props.ref ======', props.ref)

  console.debug('refP ======', refP)

  return (
    <div className="chef-recipe">
      <h2>Chef Recipes</h2>

      <div className="chef-recipe-list">
        <ul>
          {
            recipes.filter(recipe => props.ingredients.includes(recipe.ingredient))
              .map(recipe => (
                <li key={recipe.id}>
                  <div ref={props.ref}>
                    <h3>{recipe.ingredient}</h3>
                    <p ref={refP}>{recipe.recipe}</p>
                  </div>
                </li>
              ))
          }
        </ul>
      </div>
    </div>
  )
}
