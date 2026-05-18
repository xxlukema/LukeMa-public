import React from 'react'
import './WindowSize.scss'

export const WindowSize = () => {

  const [windowWidth, setWindowWidth] = React.useState<number>(window.innerWidth)

  React.useEffect(() => {

    /**
     * event listner to add/remove
     */
    const watchWindowWidth = () => {
      console.debug('Resized')
      setWindowWidth(window.innerWidth)
    }

    /**
     * add event listner on compoenent init
     */
    window.addEventListener('resize', watchWindowWidth)

    /**
     * returns a cleanup function
     */
    return () => {
      /**
       * remove event listner on compoenent destroy
       */
      console.debug('Cleaning up...')
      window.removeEventListener('resize', watchWindowWidth)
    }
  }, [])  /** `[]` --- (always have this parameter) The empty dependency array means to run only once at component initiates. */

  return (
    <div>
      <div>Window width: {windowWidth}</div>
    </div>
  )
}
