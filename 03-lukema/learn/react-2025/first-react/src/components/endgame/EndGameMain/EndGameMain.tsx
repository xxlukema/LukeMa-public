import clsx from 'clsx';
import React from 'react';
import Confetti from 'react-confetti';
import './EndGameMain.scss';


export const EndGameMain = () => {

  /**
   * Trick! Use arrow function to initialize the state for **lazy init**.
   */
  const languages: string[] = ['Python', 'HTML', 'Java', 'Go', 'React', 'Angular', 'Node', 'Assembly']
  const colors: string[] = ['#109F52', '#007ACC', '#DD0031', '#68A063', '#B12525', '#B52E31', '#026E00', '#000000']

  const maxMissed = languages.length - 1;
  const missedGussCount = React.useRef<number>(0);

  const [matchedLetters, setMatchedLetters] = React.useState(new Set<string>());
  const [missedLetters, setMissedLetters] = React.useState(new Set<string>());

  const [word, setWord] = React.useState<string>('');
  const [displayWord, setDisplayWord] = React.useState<string>('');

  const [result, setResult] = React.useState<string>('');

  const [keyboard, setKeyboard] = React.useState<string[]>([]);

  React.useEffect(() => {
    setKeyboard(Array.from({ length: 26 }, (_, i) => String.fromCharCode(65 + i)));
    setWord(languages[Math.floor(Math.random() * (languages.length - 1))]);
    setResult('')
  }, []);

  React.useEffect(() => {
    setDisplayWord(word.toUpperCase().split('').map(letter => matchedLetters.has(letter) ? letter : ' ').join(''));
  }, [word, matchedLetters]);

  React.useEffect(() => {
    if (displayWord.length > 0 && displayWord === word.toUpperCase()) {
      setResult('win');
    }
  }, [displayWord, word]);


  console.debug('word', word, 'displayWord', `#${displayWord}#`);

  const styles = {
    backgroundColor: clsx({
      '#109F52': result === 'win',
      '#B12525': result === 'lose'
    })
  }

  const refKeyboard = React.useRef<HTMLButtonElement>(null);
  const refLanguage = React.useRef<HTMLDivElement>(null);

  const handleKeyStrick = (key: string) => {
    if (word.toUpperCase().includes(key)) {
      if (!matchedLetters.has(key)) {
        matchedLetters.add(key);
        setMatchedLetters(new Set<string>(matchedLetters));
      }
    } else if (!missedLetters.has(key)) {
      missedLetters.add(key);
      setMissedLetters(new Set<string>(missedLetters));
      missedGussCount.current++;
      if (missedGussCount.current >= maxMissed) {
        setResult('lose');
      }
    }

    /**
    if (refKeyboard.current) {
      refKeyboard.current.disabled = true;
    }
    */
  }

  console.debug('matchedLetters', matchedLetters, 'displayWord', `#${displayWord}#`);

  const resetGame = () => {
    setWord(languages[Math.floor(Math.random() * (languages.length - 1))]);
    setMatchedLetters(new Set<string>());
    setMissedLetters(new Set<string>());
    missedGussCount.current = 0;
    setResult('');
  }

  return (
    <div className="end-game-main">
      {
        result === 'win' &&
        <Confetti
          recycle={false}
          numberOfPieces={200}
        />
      }
      <div className="end-game-main__result" style={styles}>
        {
          result === 'win' && (
            <>
              <div className="end-game-main__result--win">You win!</div>
              <p className="end-game-main__result--win">You saved the programming world from Assembly only language.</p>
            </>
          )
        }
        {
          result === 'lose' && (
            <>
              <div className="end-game-main__result--lose">Game over!</div>
              <p className="end-game-main__result--lose">
                You lose. The programming world will stay dark with Assembly as the only programming language.
              </p>
            </>
          )
        }
        {
          result === '' && (
            <>
              <div className="end-game-main__result--none">&nbsp;</div>
              <p className="end-game-main__result--none">&nbsp;</p>
            </>
          )
        }
      </div>
      <div className="end-game-main__languages">
        {
          languages.map((language, idx) => (
            <div ref={refLanguage} key={language}
              className={`end-game-main__languages--item ${idx < missedGussCount.current ? 'lost' : ''}`}
              style={{ backgroundColor: idx < missedGussCount.current ? 'black' : colors[idx] }}
            >
              {language}
            </div>
          ))
        }
      </div>
      <div>Word: {word}</div>
      <div className="end-game-main__word">
        {
          displayWord.split('').map((letter, idx) => (
            <div key={letter + idx} className="end-game-main__word--letter">
              {letter}
            </div>
          ))
        }
      </div>
      <div className="end-game-main__keyboard">
        {
          keyboard.map((key) => (
            <button ref={refKeyboard} key={key} id={key}
              disabled={matchedLetters.has(key) || missedLetters.has(key)}
              style={{ backgroundColor: clsx({ 'green': matchedLetters.has(key), 'darkgrey': missedLetters.has(key) }) }}
              className="end-game-main__keyboard--key"
              onClick={() => handleKeyStrick(key)}>
              {key}
            </button>
          ))
        }
      </div>
      <button className="end-game-main__button" onClick={resetGame}>New Game</button>
    </div >
  );
};
