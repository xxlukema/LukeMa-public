import { StateMainProps } from '@/components/state/types/StateTypes';
import './StateHeader.scss';


export const StateHeader = (props: StateMainProps) => {
  return (
    <header className="state-header">
      <link href='https://fonts.googleapis.com/css?family=Inter' rel='stylesheet'></link>
      <h3>State Header</h3>
      <p>Hello {props.name}!</p>
      <hr />
    </header>
  );
};
