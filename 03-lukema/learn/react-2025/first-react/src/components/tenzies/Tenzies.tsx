import Footer from "@/components/footer/Footer"
import { TenziesHeader } from "./TenziesHeader/TenziesHeader"
import { TenziesMain } from "./TenziesMain/TenziesMain"
import './Tenzies.scss'

export const Tenzies = () => {

  return (
    <div className="tenzies-main">
      <TenziesHeader />
      <TenziesMain />
      <Footer />
    </div>
  )

}
