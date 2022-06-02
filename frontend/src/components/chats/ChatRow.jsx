import React from 'react'
import '../../styles/style.css'

const ChatRow = ({currentRoom, room, roomChange}) => {
  const rowStyle = () => {
    let className = 'chatRow';
    if (currentRoom.id === room.id)
      className += ' active'
    return className;
  }

  return (
    <div className={rowStyle()} onClick={() => roomChange(room)}>
      {room.image != null ? <img className='chatImage' src={room.image.filename} alt="" /> :
          <div className='chatImage chatImagePlaceholder'/>}
      <p className='rowText'>{room.id} {room.name}</p>
    </div>
  )
}

export default ChatRow