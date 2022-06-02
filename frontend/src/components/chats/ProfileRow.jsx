import React, {useEffect, useState, useContext} from 'react'
import { Context } from "../Context"
import axios from "axios";
import { URL } from "../../const"
import '../../styles/style.css'
import Modal from "react-modal";

const ProfileRow = (props) => {
  const [token, setToken] = useContext(Context);
  const [isOpen, setIsOpen] = useState(false);
  const [image, setImage] = useState(null);

  const handleViewModal = () => setIsOpen(!isOpen);

  const uploadImage = () => {
      if (image != null) {
          let formData = new FormData();
          formData.append("file", image);
          axios({
              method: "post",
              url: URL + "api/user/upload",
              data: formData,
              headers: { "Content-Type": "multipart/form-data",
                  "Authorization": 'Bearer ' + token},
          }).then(() => console.log("success!"))
      }
  }

  return (
    <div className='chatRow profileBorder'>
        {props.user.image != null ? <img className='chatImage' src={props.user.image.filename} onClick={handleViewModal}/> :
            <div className='chatImage chatImagePlaceholder' onClick={handleViewModal}/>}
      <p className='rowText'>{props.user.firstName} {props.user.secondName}</p>
        <Modal isOpen={isOpen} appElement={this}>
            <h1>Загрузить изображение</h1>
            <form>
                <input type="file" onChange={(event) => setImage(event.target.files[0])}/>
                <input type="submit" onClick={uploadImage}/>
            </form>

            <button onClick={handleViewModal}>Закрыть окно</button>
        </Modal>
    </div>
  )
}

export default ProfileRow