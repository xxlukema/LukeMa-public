import React from 'react'
import { hexToComplimentary, invertColor, loadData } from './utils.js';
const { useState, useEffect, useRef } = React;
const utm = "?utm_source=scrimba_degree&utm_medium=referral";

export const AiLearning = (props: any) => {
  let [photos, setPhotos] = useState([]);

  // CHALLENGE: REPLACE MY NAME AND HOBBY WITH YOURS!
  let [name, setName] = useState("Luke");
  let [hobby, setHobby] = useState("Boxing");

  // PS: When you are done, click the play button in the
  // bottom left corner to continue watching the scrim

  const hobbyInput = useRef(null);

  const numberOfPhotos = 1;
  const url = "https://apis.scrimba.com/unsplash/photos/random/?count=" + numberOfPhotos;

  useEffect(() => {
    const photosUrl = hobby ? `${url}&query=${hobby}` : url;
    loadData({
      url: photosUrl,
      onSuccess: (res: React.SetStateAction<never[]>) => {
        setPhotos(res);
      }
    });
  }, [hobby, url]);

  return (
    <div className="container">
      {hobby ? photos.map(photo => {
        const complementaryColor = hexToComplimentary(photo.color)
        const invertedColor = invertColor(photo.color, "#000000")
        return (
          <div key={photo.id} className="item">
            <div
              className="img"
              style={{
                backgroundImage: `url("${photo.urls.regular}")`
              }}
            />
            <div className="frame" style={{ backgroundColor: invertedColor }}>
            </div>
            <div className="text">
              <h4 className="name" style={{ color: photo.color, backgroundColor: invertedColor }}>My name is {name}</h4>
              <div className="divider" style={{ backgroundColor: complementaryColor }}></div>
              <h5 className="position" style={{ color: invertedColor, backgroundColor: photo.color }}>I love {hobby}</h5>
            </div>
            <div className="caption">
              <span className="credits">
                Photo by
                <a href={photo.user.links.html + utm}>
                  {photo.user.name}
                </a>
                <span> on </span>
                <a href={"https://unsplash.com" + utm}>
                  Unsplash
                </a>
              </span>
            </div>
          </div>
        );
      }) : ""}
    </div>
  );
};

