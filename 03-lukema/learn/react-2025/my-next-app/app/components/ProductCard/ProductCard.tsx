import AddToCart from './AddToCart'


const ProductCard = () => {
  return (
    <fieldset className='border-2 border-gray-500 rounded-lg p-5'>
      <legend className='text-2xl font-bold'>Product Card</legend>
      <div className='p-5 my-5 bg-sky-400 text-red text-xl hover:bg-sky-600'>
        <AddToCart />
      </div>
      <div>
        <div className='text-3xl font-bold underline'>Hello world!</div>
        <p className='text-2xl'>Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam, voluptatibus.</p>
        <button className='button bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'>
          Button
        </button>
      </div>
    </fieldset>
  )
}

export default ProductCard
