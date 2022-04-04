import React, {useState} from 'react'

const Registration = () => {
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [secondName, setSecondName] = useState("");
    
  
    const subm = () => {
      console.log(login);
      console.log(password);
      console.log(firstName);
      console.log(secondName);
    }
  
    return (
        <div>
            <form>
                <input type="email" value={login} onChange={(e) => setLogin(e.target.value)} placeholder="Email" />
                <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" />
                <input type="password" value={firstName} onChange={(e) => setFirstName(e.target.value)} placeholder="First Name" />
                <input type="password" value={secondName} onChange={(e) => setSecondName(e.target.value)} placeholder="Second Name" />
                <input type="submit" value="Отправить" onSubmit={subm} />
            </form>
        </div>
    );
}

export default Registration