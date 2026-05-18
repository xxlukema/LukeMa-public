import { recipes } from '@/components/chef/ChefRecipe/RecipeData';
import { ChefState } from 'src/components/chef/types/ChefState';
import './ChefIngredient.scss';

export const ChefIngredient = (props: ChefState) => {

  const acceptibleIngredients = recipes.map((recipe) => recipe.ingredient)
  console.log(acceptibleIngredients);

  const addIngredient = (ingredient: string) => {

    if (!ingredient) {
      return
    }

    ingredient = ingredient.charAt(0).toUpperCase() + ingredient.slice(1).toLowerCase()

    if (acceptibleIngredients.includes(ingredient)) {
      if (!props.ingredients.includes(ingredient)) {
        props.updateIngreients([...props.ingredients, ingredient].sort((a, b) => a.localeCompare(b)));
      }
    }
  }

  const handleAction = (formData: FormData) => {
    console.log('Action', formData);

    const newIngredient = formData.get('ingredient') as string;
    addIngredient(newIngredient);
  }

  return (
    <div className="chef-main">
      {
        /**
        <form className='chef-form' onSubmit={handleSubmit} method='post'>
         */
      }
      <form className='chef-form' action={handleAction} >
        <input type="text" name='ingredient' placeholder='e.g. Onion' />
        <button type="submit">Submit</button>
      </form>

      <div>
        <h2>Ingredients</h2>
        <ul className='ingredients'>
          {
            props.ingredients.map((ingredient) => (
              <li key={ingredient}>{ingredient}</li>
            ))
          }
        </ul>
      </div>
    </div >
  );

};
