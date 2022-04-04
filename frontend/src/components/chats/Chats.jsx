import React, {useEffect, useState} from 'react'
import axios from 'axios';
import { URL } from '../../const';
import ChatsList from './ChatsList';
import ProfileRow from './ProfileRow';
import ToolsRow from './ToolsRow';
import '../../styles/style.css'

const Chats = (props) => {
    return (
        <div className='chats chatBorder interface'>
            <ProfileRow user={props.user} />
            <ChatsList roomChange={props.roomChange} currentRoom={props.currentRoom}/>
            <ToolsRow />
        </div>
    );
}

export default Chats