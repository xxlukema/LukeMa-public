import Footer from "@/components/footer/Footer";
import React from "react";
import { StateHeader } from "./StateHeader/StateHeader";
import { StateMain } from "./StateMain/StateMain";
import { StateMainProps } from "./types/StateTypes";
import './State.scss';


export const State = () => {

  const [rootState, setRootState] = React.useState<StateMainProps>(
    {
      name: '',
      clickHandler: () => {}
    })

  React.useEffect(() => {
    setRootState(prev => {
      return {
        ...prev,
        name: 'world'
      }
    })
  }, []);  /** `[]` --- (always have this parameter) The empty dependency array means to run only once at component initiates. */

  const clickHandler = (name: string) => {
    setRootState(prev => {
      return {
        ...prev,
        name
      }
    })
  }

  return (
    <div className="state-main">
      <StateHeader {...rootState} />
      <StateMain {...rootState} clickHandler={clickHandler} />
      <Footer />
    </div>
  );
}
