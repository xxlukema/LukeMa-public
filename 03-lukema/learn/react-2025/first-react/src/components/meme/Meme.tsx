import Footer from "@/components/footer/Footer"
import { Header } from "../header/Header"
import { MemeMain } from "./MemeMain/MemeMain"

export const Meme = () => {

  return (
    <div className='page'>
      <Header />
      <MemeMain />
      <Footer></Footer>
    </div>
  )
}
