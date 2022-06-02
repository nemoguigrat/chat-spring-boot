import React, {useEffect, useState, useContext} from 'react'
import { Context } from "../Context"
import axios from "axios";
import { URL } from "../../const"
import '../../styles/style.css'

const ProfileRow = (props) => {
  const [token, setToken] = useContext(Context);

  return (
    <div className='chatRow profileBorder'>
        {props.user.image != null ? <img className='chatImage' src={props.user.image.filename} alt="" /> :
            <div className='chatImage chatImagePlaceholder'/>}
      <p className='rowText'>{props.user.firstName} {props.user.secondName}</p>
    </div>
  )
}

export default ProfileRow