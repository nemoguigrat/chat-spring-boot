import React from 'react'
import '../../styles/style.css'

const ProfileRow = (props) => {
  return (
    <div className='chatRow profileBorder'>
      <div className='chatImage'></div>
      <p className='rowText'>{props.user.firstName} {props.user.secondName}</p>
    </div>
  )
}

export default ProfileRow