import {useContext} from "react";
import {CustomContext} from "../../utils/Context";

const FrindBlock = ({friend, flagAddFriend, flagDeleteFriend}) => {

    const user = useContext(CustomContext);

    const addFriend = async () => {
        try {
            const requestOptions = {
                method: "POST"
            }
            const response = await fetch(`http://localhost:8081/api/v1/customer/sendApplicationToFriend?myId=${user?.user?.id}&friendId=${friend?.id}`, requestOptions)
            if (response.ok) {
                alert("Заявка в друзья отправлена.")
            } else {
                const result = await response.text();
                alert(result);
            }
        } catch (error) {
            console.error("Ошибка сети: " + error)
        }
    }

    const deleteFriend = async () => {
        try {
            const requestOptions = {
                method: "POST"
            }
            const response = await fetch(`http://localhost:8081/api/v1/customer/deleteFriend?myId=${user?.user?.id}&friendId=${friend?.id}`, requestOptions)
            if (response.ok) {
                const result = await response.text();
                alert(result)
                window.location.reload();
            } else {
                const result = await response.text();
                alert(result);
            }
        } catch (error) {
            console.error("Ошибка сети: " + error)
        }
    }

    return (
        <div className='review_block'>
            <div>
                <p className='str' style={{paddingLeft: "10px"}}>Фамилия: {friend?.surname}</p>
                <p className='str' style={{paddingLeft: "10px"}}>Имя: {friend?.name}</p>
                <p className='str' style={{paddingLeft: "10px"}}>Отчество: {friend?.patronymic}</p>
                <p className='str' style={{paddingLeft: "10px"}}>Почта: {friend?.email}</p>
            </div>
            <div style={{paddingTop: "20px"}}>
                {flagAddFriend && <button className='button' onClick={addFriend}>Добавить в друзья</button>}
                {flagDeleteFriend && <button className='button' onClick={deleteFriend}>Удалить из друзей</button>}
            </div>
        </div>

    )

}

export default FrindBlock;