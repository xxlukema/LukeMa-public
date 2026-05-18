import './OpenAiHeader.scss'
import OpenAiLogo from '@/assets/openai/OpenAI_logo_PNG_(5).png'

export const OpenAiHeader = () => {
  return (
    <div className="open-ai-header">
      <div className="open-ai-header__logo">
        <img src={OpenAiLogo} alt="OpenAI logo" />
      </div>
      <div className="open-ai-header__title">
        <h3>OpenAIPlateform</h3>
      </div>
    </div>
  )
}
