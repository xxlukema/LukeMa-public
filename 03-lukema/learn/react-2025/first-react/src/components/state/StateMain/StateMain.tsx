import { StateMainProps } from '@/components/state/types/StateTypes';
import './StateMain.scss';


export const StateMain = (props: StateMainProps) => {

  console.log('StateMain', props);

  const name = props.name === 'Mr. Joe' ? 'world' : 'Mr. Joe';

  return (
    <main className="state-main">
      <h1>State Main</h1>
      <p>Hello, {props.name}!</p>
      <button onClick={() => props.clickHandler(name)}>Say hello to {name}</button>
    </main>
  );
};
