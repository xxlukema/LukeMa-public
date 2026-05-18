// import JapanMap from 'src/assets/travel/japan-map.png'
import './Travel.css'
import { FaLocationDot } from "react-icons/fa6";

interface MapProps {
  img: string;
  alt: string;
}

interface TravelProps {
  map: MapProps;
  location: string;
  url: string;
  title: string;
  date: string;
  desc: string;
}

export const Travel = (props: TravelProps) => {

  console.debug('Travel', props)

  return (
    <div className='travel'>
      <img className='map' src={props.map.img} alt={props.map.alt} />
      <div className='travel-info'>
        <div className='travel-header'>
          <FaLocationDot />
          <h5>{props.location}</h5>
          <a href={props.url}>View on Google Map</a>
        </div>
        <div className='travel-title'>{props.title}</div>
        <p className='travel-date'>{props.date}</p>
        <p className='travel-desc'>{props.desc}</p>
      </div>
    </div>
  )
}
