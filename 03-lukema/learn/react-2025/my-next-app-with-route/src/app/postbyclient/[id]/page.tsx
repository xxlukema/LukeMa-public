import React from 'react'

const IdPage = ({ params }: { params: { id: string } }) => {

  const str = `Id Page ${params.id}`

  return (
    <>
      <div>Id Page {params.id}</div>
      <p>{str}</p>
    </>
  )
}

export default IdPage
