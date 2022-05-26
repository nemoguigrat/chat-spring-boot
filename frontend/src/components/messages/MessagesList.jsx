import React, {useState, useEffect, useRef, useLayoutEffect, useContext} from 'react'
import axios from 'axios';
import {Context} from '../Context';
import {URL} from '../../const';
import Message from './Message';

const MessagesList = (props) => {
    const reference = useRef(null);
    const [messages, setMessages] = useState([]);
    const [token, setToken] = useContext(Context);

    const getMessages = async () => {
        if (!props.currentRoom.id)
            return;
        let resp = await axios.get(URL + "api/chat/messages/" + props.currentRoom.id, {headers: {Authorization: 'Bearer ' + token}});
        console.log(resp);
        let respMessages = await resp.data;
        setMessages(respMessages);
    }

    const scrollToBottom = () => reference.current?.scrollIntoView({
        behavior: "smooth",
        top: reference.current.offsetTop
    });

    useEffect(() => getMessages(), [props.currentRoom]);


    useEffect(() => {
        if (props.data != null && props.currentRoom.id === props.data.room) {
            let copy = Object.assign([], messages)
            copy.push(props.data)
            setMessages(copy)
        }
    }, [props.data]);

    useEffect(() => scrollToBottom, [messages])

    return (
        <div className='messagesList customScroll'>
            {messages.length > 0 ? (messages.map(message =>
                    <Message key={message.id} message={message} user={props.user}/>)) :
                <p className="noMessagesText">В чате нет сообщений.</p>}
            <div ref={reference}/>
        </div>
    )
}

export default MessagesList