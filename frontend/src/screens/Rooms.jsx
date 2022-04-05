import React, {useState, useEffect, useRef, useCallback, useContext} from 'react'
import Chats from '../components/chats/Chats'
import Messages from '../components/messages/Messages'
import { WEBSOCKET, URL } from '../const';
import { useNavigate } from 'react-router-dom';
import { Context } from '../components/Context';
import axios from 'axios';
import "../styles/style.css"

const Rooms = (props) => {
  const [currentRoom, setCurrentRoom] = useState({});
  const [isPaused, setIsPaused] = useState(false);
  const [data, setData] = useState(null);
  const [status, setStatus] = useState("");
  const [token, setToken] = useContext(Context);
  const [user, setUser] = useState({})
  const navigate = useNavigate();
  const ws = useRef(null);

  const current = (room) => {
    // console.log(id);
    setCurrentRoom(room);
    // console.log(currentRoom);
  }

  const getUser = async() => {
    let response = await axios.get(URL + 'api/user', {headers: {Authorization: "Bearer " + token}})
    setUser(response.data)
  }

  useEffect(() => {
    console.log(token + " " + 1)
    if (token != "")
      getUser();
  }, [token])

  useEffect(() => {
        if (token == "")
          return;
        if (!isPaused) {
            ws.current = new WebSocket(WEBSOCKET + "?token=" + token); // создаем ws соединение
            ws.current.onopen = () => console.log("Соединение открыто");  // callback на ивент открытия соединения
            ws.current.onclose = () => console.log("Соединение закрыто"); // callback на ивент закрытия соединения

            gettingData();
        }

        return () => ws.current.close(); // кода меняется isPaused - соединение закрывается
    }, [ws, isPaused, token]);

    const gettingData = useCallback(() => {
        if (!ws.current) return;
        ws.current.onmessage = e => {                //подписка на получение данных по вебсокету
            if (isPaused) return;
            const message = JSON.parse(e.data);
            setData(message);
            console.log(message);
        };
    }, [isPaused]);

    const sendMessage = (message) => {
        if (!ws.current) return;
        ws.current.send(message)
    }

  return (
    <div className='rooms interface'>
        <div className='chatapp'>
          <Chats roomChange={current} currentRoom={currentRoom} user={user}/>
          <Messages data={data} currentRoom={currentRoom} sendMessage={sendMessage} user={user}/>
        </div>
    </div>
  )
}

export default Rooms