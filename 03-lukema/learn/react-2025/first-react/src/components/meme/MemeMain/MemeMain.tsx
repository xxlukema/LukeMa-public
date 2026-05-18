import './MemeMain.scss'
import { memeDataArray } from './MemeDataArray';
import React from 'react'

interface Skywalker {
  name: string;
  height: string;
  mass: string;
  hair_color: string;
  skin_color: string;
  eye_color: string;
  birth_year: string;
  gender: string;
  homeworld: string;
  films: string[];
  species: string[];
  vehicles: string[];
  starships: string[];
  created: string;
  edited: string;
  url: string;
}

interface Meme {
  id: string;
  name: string;
  url: string;
  width: number;
  height: number;
  box_count: number;
  captions: number;
}

export const MemeMain = () => {

  const [skywalker, setSkywalker] = React.useState<Skywalker>(
    {
      "name": "John",
      "height": "172",
      "mass": "77",
      "hair_color": "blond",
      "skin_color": "fair",
      "eye_color": "blue",
      "birth_year": "19BBY",
      "gender": "male",
      "homeworld": "https://swapi.dev/api/planets/1/",
      "films": [],
      "species": [],
      "vehicles": [],
      "starships": [],
      "created": "2014-12-09T13:50:51.644000Z",
      "edited": "2014-12-20T21:17:56.891000Z",
      "url": "https://swapi.dev/api/people/1/"
    }
  )

  const [allMemes, setAllMemes] = React.useState<Meme[]>([]);

  React.useEffect(() => {

    /**
     * TODO: This line actually runs twice. Why?
     */
    console.debug('This will run once at component initiates. Fetching all memes...')

    fetch("https://api.imgflip.com/get_memes")
      .then(res => res.json())
      .then(data => setAllMemes(data.data.memes))
  }, [])  /** `[]` --- (always have this parameter) The empty dependency array means to run only once at component initiates. */

  /**
   *  This line will run every time a state changes.
   */
  /* console.debug(`--- This line will run every time a state changes. All memes length: ${allMemes.length} ---`) */

  const [pos, setPos] = React.useState<number>(0)

  // const urlTemplate = `https://swapi.dev/api/people/${pos + 1}`

  const [url, setUrl] = React.useState<string>(`https://swapi.dev/api/people/${pos + 1}`)

  React.useEffect(() => {

    console.debug('----------11111', url)

    fetch(url)
      .then(res => res.json())
      .then(data => {
        console.debug('-----', data.name)
        setSkywalker(data)
      })
  }, [url]) /** <=== if the dependency array is empty, then run this only once at initial load of the component. */

  React.useEffect(() => {
    setUrl(`https://swapi.dev/api/people/${pos + 1}`)
  }, [pos]) /** <=== if the dependency array is empty, then run this only once at initial load of the component. */

  console.debug('MemeMain is rendered')

  const clickHandler = () => {
    console.debug('================ clickHandler is called before', pos)

    /**
     * random number
     */
    const randomNumber = Math.floor(Math.random() * allMemes.length)
    console.debug('randomNumber', randomNumber)

    setPos((pos + 1) % memeDataArray.length)
  }

  return (
    <div className="meme-main">
      <div className='add-count'>
        <div>skywalker: {skywalker.name}</div>
        {/* <pre>{JSON.stringify(skywalker, null, 2)}</pre> */}
      </div>

      <div className="text-fields">
        <div className="text-display">
          <label htmlFor="top-text">Top Text</label>
          <input type="text" id="top-text" name="top-text" placeholder="Top text" value={memeDataArray[pos].topText} />
        </div>
        <div className="text-display">
          <label htmlFor="bottom-text">Bottom Text</label>
          <input type="text" id="bottom-text" name="bottom-text" placeholder="Bottom text" value={memeDataArray[pos].bottomText} />
        </div>
      </div>
      <button className="generate-meme" onClick={clickHandler}>Generate a new meme image</button>
      {
        <div className="meme-container">
          {
            <div className="meme-item" style={{ backgroundImage: `url(${memeDataArray[pos].memeUrl})` }}>
              <div>{memeDataArray[pos].topText}</div>
              <div>{memeDataArray[pos].bottomText}</div>
            </div>
          }
        </div>
      }
    </div>
  )
}
