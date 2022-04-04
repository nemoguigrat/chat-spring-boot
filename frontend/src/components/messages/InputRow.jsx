import React, {useState} from 'react'
import '../../styles/style.css'

const InputRow = (props) => {
  const [value, setValue] = useState('');

  const sendMessage = () => {
    let message = {
      message: value,
      room: props.currentRoom.id
    }
    props.sendMessage(JSON.stringify(message));
  }

  return (
    <div className='inputRow'>
        <div className='messageInput' contentEditable={true} onInput={v => setValue(v.target.innerText)}></div>
        <button onClick={() => sendMessage()}> > </button>
    </div>
  )
}

export default InputRow