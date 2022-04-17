import React, {useState} from 'react'
import axios from "axios";
import {URL} from "../const";
import { useNavigate } from 'react-router-dom'

const Registration = () => {
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [secondName, setSecondName] = useState("");
    const navigate = useNavigate();

    const subm = async () => {
        console.log(login);
        console.log(password);
        console.log(firstName);
        console.log(secondName);
        let response = await axios.post(URL + "api/auth/registration", {
            email: login,
            password: password,
            firstName: firstName,
            secondName: secondName
        }).catch((error) => console.log(error.response)); //TODO показать сообщение об ошибке
        if (response.status === 201)
            navigate("/login", {replace: true})
    }

    return (
        <div>
            <form>
                <input type="email" value={login} onChange={(e) => setLogin(e.target.value)} placeholder="Email"/>
                <input type="password" value={password} onChange={(e) => setPassword(e.target.value)}
                       placeholder="Password"/>
                <input type="text" value={firstName} onChange={(e) => setFirstName(e.target.value)}
                       placeholder="First Name"/>
                <input type="text" value={secondName} onChange={(e) => setSecondName(e.target.value)}
                       placeholder="Second Name"/>
                <input type="button" value="Отправить" onClick={subm}/>
            </form>
        </div>
    );
}

export default Registration