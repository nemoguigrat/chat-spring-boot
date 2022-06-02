import React, {useState, useContext} from 'react'
import '../../styles/style.css'
import Modal from "react-modal";
import axios from "axios";
import { URL } from "../../const";
import {Context} from "../Context";

const InfoRow = (props) => {
    const [isOpen, setIsOpen] = useState(false);
    const [token, setToken] = useContext(Context);
    const [image, setImage] = useState(null);

    const handleViewModal = () => setIsOpen(!isOpen);

    const uploadImage = () => {
        console.log(props.currentRoom.id)
        if (image != null) {
            let formData = new FormData();
            formData.append("file", image);
            axios({
                method: "post",
                url: URL + "api/chat/room/" + props.currentRoom.id + "/upload",
                data: formData,
                headers: { "Content-Type": "multipart/form-data",
                    "Authorization": 'Bearer ' + token},
            }).then(() => console.log("success!"))
        }
    }
    
  return (
    <div className='infoRow interface profileBorder'>
        {props.currentRoom.image != null ? <img className='chatImage' src={props.currentRoom.image.filename} onClick={handleViewModal}/> :
            <div className='chatImage chatImagePlaceholder' onClick={handleViewModal}/>}
      <div className='rowText'>{props.currentRoom.name}</div>
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

export default InfoRow