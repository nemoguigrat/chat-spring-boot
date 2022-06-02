import React from 'react'
import '../../styles/style.css'

const InfoRow = (props) => {
  return (
    <div className='infoRow interface profileBorder'>
        {props.currentRoom.image != null ? <img className='chatImage' src={props.currentRoom.image.filename} alt="" /> :
            <div className='chatImage chatImagePlaceholder' />}
      <div className='rowText'>{props.currentRoom.name}</div>
    </div>
  )
}

export default InfoRow