import React, { useState, useContext } from 'react'
import PropTypes from 'prop-types';
import { Context } from '../components/Context';
import axios from 'axios';
import { URL } from '../const';
import { useNavigate } from 'react-router-dom';

const Login = (props) => {
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const [token, setToken] = useContext(Context);
  const navigate = useNavigate();

  const subm = async() => {
    console.log(login);
    console.log(password);
    let response = await axios.post(URL + "api/auth/login", {
      email: login,
      password: password
    });
    if (response.status === 200){
      setToken(response.data.token);
      localStorage.setItem("token", response.data.token)
    }
    navigate("/", {replace: true})
  }

  return (
      <div>
          <form>
              <input type="email" value={login} onChange={(e) => setLogin(e.target.value)} placeholder="Email" />
              <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" />
              <input type="button" value="Отправить" onClick={subm} />
          </form>
      </div>
  );
};

Login.propTypes = {};

export default Login;
