import React from 'react'
import '../../styles/style.css'

const Message = ({message, user}) => {
  const messageStyle = () => {
    let className = 'message';
    if (user.id === message.senderId)
      className += ' currentUserMessage'
    return className;
  }

  return (
      <div className='messageBox'>
        <div className={messageStyle()}>
          <p className='messageSender'>
            {message.firstName} {message.secondName}</p>
          <p className='messageText'>{message.message}</p>
        </div>
        <div className='messageDate'>{new Date(Date.parse(message.dateCreation)).toLocaleString()}</div>
      </div>
  )
}

export default Message