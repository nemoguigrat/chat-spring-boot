import React, {useEffect, useState, useContext} from 'react'
import { Context } from '../Context';
import axios from 'axios';
import { URL } from '../../const';
import ChatRow from './ChatRow';

const ChatsList = (props) => {
    const [rooms, setRooms] = useState([]);
    const [token, setToken] = useContext(Context);

    const getRooms = async() => {
        let resp = await axios.get(URL + "api/chat/rooms", {headers: {Authorization: 'Bearer ' + token}});
        let rooms = await resp.data;
        setRooms(rooms);
        console.log(rooms)
        console.log(rooms.map(room => room.name));
    }

    useEffect(() => {
        if (token != "")
            getRooms();
    }, [token]);

    return (
        <div className='chatList customScroll'>
            { rooms.length > 0 ? rooms.map(room => 
                (
                    <ChatRow key={room.id} currentRoom={props.currentRoom} room={room} roomChange={props.roomChange} />
                ))  : 
                "Присоединитесь к комнате или создайте свою" 
            }
        </div>
    );
}

export default ChatsList