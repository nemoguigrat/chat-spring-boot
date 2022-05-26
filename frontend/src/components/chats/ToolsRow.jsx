import React, {useContext, useState} from 'react'
import '../../styles/style.css'
import Modal from "react-modal";
import axios from "axios";
import {URL} from "../../const";
import {Context} from "../Context";
import {useNavigate} from "react-router-dom"

const ToolsRow = () => {
    const [isOpen, setIsOpen] = useState(false);
    const [typeInModal, setTypeInModal] = useState(0);
    const [token, setToken] = useContext(Context)
    const [selectedForCommunity, setSelectedCommunity] = useState([]);
    const [selectedPersonal, setSelectedPersonal] = useState({});
    const [searchedUsers, setSearchedUsers] = useState([]);
    const navigate = useNavigate();

    const handleViewModal = () => setIsOpen(!isOpen);

    const exit = () => {
        localStorage.removeItem("token");
        navigate('/login', {replace: true})
    }

    const getUsers = async(event) => {
        let resp = await axios.get(URL + "api/users",
            {headers: {Authorization: "Bearer " + token},
                params: {searchName: event.target.form[0].value}});
        let users = await resp.data;
        console.log(users)
        setSearchedUsers(users);
    }

    const createPersonalRoom = async() => {
        if (selectedPersonal !== {}){
            console.log(selectedPersonal)
            await axios.post(URL + "api/chat/personal-rooms/" + selectedPersonal.id, {},
                {headers: {Authorization: 'Bearer ' + token}})
        } else {
            console.log("Нет выбранных пользователей!")
        }
    }

    const createCommunityRoom = async(roomName) => {
        await axios.post(URL + "api/chat/community-rooms",
            { name: roomName,
                users: selectedForCommunity.map(user => user.id)},
            {headers: {Authorization: "Bearer " + token}})
    }

    const addOrRemoveFromCommunityList = (user) => {
        console.log(selectedForCommunity)
        if (!selectedForCommunity.includes(user)) {
            setSelectedCommunity(prev => [...prev, user])
        } else {
            setSelectedCommunity(prev => {
                let values = [...prev];
                values.splice(values.indexOf(user), 1)
                return values;
            })
        }

    }

    return (
        <div className='chatRow toolsBorder'>
            <Modal isOpen={isOpen} appElement={this}>
                <h1 id="heading">Создать комнату</h1>
                <select value={typeInModal}
                        onChange={(event) =>
                            setTypeInModal(parseInt(event.target.value, 10))}>
                    <option value={0}>Диалог</option>
                    <option value={1}>Группа</option>
                </select>
                {typeInModal === 0 ?
                    <>
                        <form>
                            <input type="text" placeholder="Поиск пользователей"/>
                            <input type="button" onClick={(event => getUsers(event))}/>
                        </form>
                        <div>
                            <p>Результаты поиска</p>
                            {searchedUsers.length > 0 ?
                                searchedUsers.map(user => (
                                    <div key={user.id} onClick={() => setSelectedPersonal(user)}>{user.firstName} {user.secondName}</div>
                                )) : "Нет результатов..."}
                            <p>{selectedPersonal.id !== undefined ? (selectedPersonal.firstName + " " + selectedPersonal.secondName) :
                            "Пользователь не выбран..."}</p>
                            <button onClick={() => createPersonalRoom()} type="submit">Создать</button>
                        </div>
                    </> :
                    <>
                        <form>
                            <input type="text" placeholder="Поиск пользователей"/>
                            <input type="button" onClick={(event => getUsers(event))}/>
                        </form>
                        <div>
                            <p>Результаты поиска</p>
                            {searchedUsers.length > 0 ?
                                searchedUsers.map(user => (
                                    <div key={user.id} onClick={() => addOrRemoveFromCommunityList(user)}>{user.firstName} {user.secondName}</div>
                                )) : "Нет результатов..."}
                            <p></p>
                            <div>
                                {selectedForCommunity.length > 0 ?
                                    selectedForCommunity.map(user => (
                                        <div key={user.id}>{user.firstName} {user.secondName}</div>
                                    )) : "Выберите собеседников..."}
                            </div>
                            <form>
                                <input type="text" placeholder="Имя команты"/>
                                <input type="submit" onClick={event => createCommunityRoom(event.target.form[0].value)} />
                            </form>
                        </div>
                    </>
                    }
                <button onClick={handleViewModal}>Close Modal</button>
            </Modal>

            <input type="button" value="+" onClick={handleViewModal}/>
            <input type="button" value="->" onClick={exit}/>
        </div>
    )
}

export default ToolsRow