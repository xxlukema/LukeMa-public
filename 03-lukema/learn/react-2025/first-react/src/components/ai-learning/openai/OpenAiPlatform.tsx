import Footer from "@/components/footer/Footer";
import { OpenAiContent } from "./OpenAiContent/OpenAiContent";
import { OpenAiHeader } from "./OpenAiHeader/OpenAiHeader"

export const OpenAiPlatform = () => {
  return (
    <div>
      <OpenAiHeader />
      <OpenAiContent />
      <Footer />
    </div>
  );
}
