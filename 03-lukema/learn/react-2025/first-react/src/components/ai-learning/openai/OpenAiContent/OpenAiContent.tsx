import OpenAI from "openai";
import { ChatCompletionMessageParam } from "openai/resources/chat/completions";
import './OpenAiContent.scss';
import React from "react";

export const OpenAiContent = () => {

  const [role, setRole] = React.useState<string>('');
  const [content, setContent] = React.useState<string | undefined | null>(undefined);

  const openai = new OpenAI({
    apiKey: process.env.OPENAI_API_KEY,
    dangerouslyAllowBrowser: true
  });

  /**
   * const model = "gpt-4o-mini";
   */
  const model = "gpt-3.5-turbo";

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const messages: ChatCompletionMessageParam[] = [
    // {"role": "user", "content": "write a haiku about ai"},
    /**
    {
      "role": "system",
      "content": "You are a helpful assistant."
    },
    {
      "role": "user",
      "content": "Who won the World Series in 2020?"
    },
    {
      "role": "assistant",
      "content": "The Los Angeles Dodgers won the World Series in 2020."
    },
    {
      "role": "user",
      "content": "Where is was played?"
    }
    */
    /**
    {
      "role": "system",
      "content": "You are a rap genius. When given a topic, create a five-line rap about that topic."
    },
    {
      "role": "user",
      "content": "Write a poem about TV screans. Describe how much exitement they bring."
    }
    */
    {
      "role": "system",
      "content": "You are a rap genius. When given a topic, create a five-line rap about that topic."
    },
    {
      "role": "user",
      "content": "Write a poem about TV screans. Describe how much exitement they bring."
    }
  ];

  const messagesQuantumComputing: ChatCompletionMessageParam[] = [
    { "role": "system", "content": "You are a helpful assistant." },
    { "role": "user", "content": "Please explain Quantum Computing." },
    /**
    { "role": "assistant", "content": "Quantum computing is a type of computing that uses quantum-mechanical phenomena, such as superposition and entanglement, to perform operations on data." },
    { "role": "user", "content": "How does it differ from classical computing?" },
    { "role": "assistant", "content": "Quantum computing differs from classical computing in that it uses quantum bits, or qubits, which can exist in multiple states at once, allowing for the processing of vast amounts of data simultaneously." },
    { "role": "user", "content": "What are some potential applications of Quantum Computing?" },
    { "role": "assistant", "content": "Some potential applications of quantum computing include cryptography, optimization problems, and simulating quantum systems." },
    { "role": "user", "content": "What are the challenges of Quantum Computing?" },
    { "role": "assistant", "content": "Some challenges of quantum computing include error correction, decoherence, and scalability." },
    { "role": "user", "content": "What is the current state of Quantum Computing research?" },
    { "role": "assistant", "content": "Quantum computing research is an active area of study, with many companies and research institutions working on developing quantum computers and algorithms." },
    { "role": "user", "content": "What are some of the leading companies in Quantum Computing?" },
    { "role": "assistant", "content": "Some of the leading companies in quantum computing include IBM, Google, Microsoft, and Rigetti Computing." },
    { "role": "user", "content": "What is the future of Quantum Computing?" },
    { "role": "assistant", "content": "The future of quantum computing is promising, with the potential to revolutionize fields such as cryptography, optimization, and materials science." },
    */
    { "role": "assistant", "content": "User is a college student interested in Quantum Computing. What should the user study to prepare for a career in this field?" },
    { "role": "assistant", "content": "To prepare for a career in quantum computing, you should study mathematics, physics, computer science, and quantum mechanics." },
    { "role": "assistant", "content": "Please explain in 100 lines." }
  ]

  React.useEffect(() => {
    const completion = openai.chat.completions.create({
      model,
      store: true,
      temperature: 0.7,
      messages: messagesQuantumComputing
    });

    completion.then((result) => {
      console.debug('role:', result.choices[0].message.role)
      console.debug('content:', result.choices[0].message.content)
      setRole(result.choices[0].message.role);
      setContent(result.choices[0].message.content);
    });
  }, []);


  return (
    <div className="open-ai-content">
      <h2>OpenAIPlatform</h2>

      {
        // completion.then((result) => {
        //   return <p> {result.choices[0].message.role}</p>
        // })
        <>
          {<div>{role}</div>}
          {<p>{content}</p>}
        </>
      }


      {/* <p>
        OpenAI is an artificial intelligence research laboratory consisting of the for-profit OpenAI LP and
        the non-profit OpenAI Inc. The company aims to ensure that artificial general intelligence benefits
        all of humanity. The organization was founded in December 2015 by Elon Musk, Sam Altman, Greg Brockman,
        Ilya Sutskever, John Schulman, and Wojciech Zaremba. OpenAI has developed a number of significant AI
        models and has been involved in a number of controversies.
      </p> */}
    </div >
  )
}
