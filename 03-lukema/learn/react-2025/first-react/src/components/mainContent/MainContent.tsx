import { useState } from 'react'
import './MainContent.scss'

export const MainContent = () => {
  const [age, setAge] = useState(20)

  return (
    <main>
      <div className='list'>
        <ol>
          <li>Age: {age}</li>
          <li>Name: Luke</li>
        </ol>
      </div>
      <button onClick={() => setAge((age) => age + 1)}>Add age</button>
    </main>
  )
}
