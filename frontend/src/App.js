import React, { useState, useEffect, useRef, useCallback } from 'react';
import { BrowserRouter, Route, Routes, useNavigate } from 'react-router-dom';
import Login from './screens/Login';
import Rooms from './screens/Rooms';
import Registration from './screens/Registration';
import { Context } from './components/Context';

const App = () => {
    const [token, setToken] = useState("");
    const navigation = useNavigate();

    useEffect(() => {
        let localToken = localStorage.getItem('token');
        if (localToken != null){
            console.log(localToken)
            setToken(localToken);
        } else {
            navigation('/login', {replace: true})
        }
    }, [])

    return (
        <Context.Provider value={[token, setToken]}>
            <Routes>
                <Route path="/" element={<Rooms />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Registration />} />
            </Routes>
        </Context.Provider>
    );
}

export default App;