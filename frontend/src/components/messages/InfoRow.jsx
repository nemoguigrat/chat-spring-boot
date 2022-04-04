import React from 'react'
import '../../styles/style.css'

const InfoRow = (props) => {
  return (
    <div className='infoRow interface profileBorder'>
      <div className='chatImage'></div>
      <div className='rowText'>{props.currentRoom.name}</div>
    </div>
  )
}

export default InfoRow