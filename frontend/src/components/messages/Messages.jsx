import React, {useState, useEffect} from 'react'
import axios from 'axios';
import { URL } from '../../const';
import '../../styles/style.css'
import MessagesList from './MessagesList';
import InfoRow from './InfoRow';
import InputRow from './InputRow';

const Messages = (props) => {
  return (
    <div className='messages'>
        <InfoRow currentRoom={props.currentRoom} />
        <div className='wrapperMessages'>
          <MessagesList data={props.data} currentRoom={props.currentRoom} user={props.user}/>
          <InputRow currentRoom={props.currentRoom} sendMessage={props.sendMessage}/>
        </div>
    </div>
    
  )
}

export default Messages