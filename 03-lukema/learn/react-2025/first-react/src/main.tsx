import { AiLearning } from '@/components/ai-learning/boxer/AiLearning'
import { OpenAiPlatform } from '@/components/ai-learning/openai/OpenAiPlatform'
import { App } from '@/components/app/App.tsx'
import { Chef } from '@/components/chef/Chef'
import { EndGame } from '@/components/endgame/EndGame'
import { Layout } from '@/components/layout/Layout'
import { Meme } from '@/components/meme/Meme'
import { State } from '@/components/state/State'
import { Tenzies } from '@/components/tenzies/Tenzies'
import { WindowTracker } from '@/components/window-tracker/WindowTracker'
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './index.scss'
import './main.scss'
import { Provider } from 'react-redux'
import { store } from './components/redux/store'


createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Provider store={store}>
      {/* <App /> */}
      {/* <Chef /> */}
      {/* <State /> */}
      {/* <Meme /> */}
      {/* <WindowTracker /> */}
      {/* <Tenzies /> */}

      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route path="openai-learning" element={<OpenAiPlatform />} />
            <Route path="ai-learning" element={<AiLearning />} />
            <Route index element={<Tenzies />} />
            <Route path="endgame" element={<EndGame />} />
            <Route path="chef" element={<Chef />} />
            <Route path="state" element={<State />} />
            <Route path="meme" element={<Meme />} />
            <Route path="windowTracker" element={<WindowTracker />} />
            <Route path="*" element={<App />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </Provider>
  </StrictMode>
)
