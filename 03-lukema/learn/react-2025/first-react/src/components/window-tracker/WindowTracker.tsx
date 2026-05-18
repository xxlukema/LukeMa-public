import React from "react"
import { WindowSize } from "./WindowSize/WindowSize"
import './WindowTracker.scss'


export const WindowTracker = () => {

  const [show, setShow] = React.useState<boolean>(true)

  const toggleShow = () => {
    console.debug('clicked', show)

    setShow(!show)
  }

  return (
    <main className="windowtracker-main">
      <button onClick={toggleShow}>Toggle Window Tracker {show ? 'true' : 'false'}!</button>
      <div>Show: {show ? 'true' : 'false'}</div>
      {show && <WindowSize />}
    </main>
  )
}
