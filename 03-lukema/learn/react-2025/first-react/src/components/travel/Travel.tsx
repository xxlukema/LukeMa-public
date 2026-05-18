
import { Header } from '@/components/header/Header'
// import { MainContent } from './components/mainContent/MainContent'
import { Travel } from './TravelMain/TravelMain'
import { travelData } from './TravelData/TravelData';
import Footer from '@/components/footer/Footer';

export const Page = () => {
  return (
    <div className='page'>
      <Header />
      { /* <MainContent /> */}
      {
        travelData.map((data) => (
          <Travel key={data.id} {...data} />
        ))
      }
      <Footer></Footer>
    </div>
  )
}
