import {useContext, useEffect, useState} from "react";
import {CustomContext} from "../utils/Context";
import Navbar from "../navbar/Navbar";
import PageName from "../pages/PageName";
import FrindBlock from "../pages/friends/FrindBlock";
import {useNavigate} from "react-router";

const Friends = () => {

    const user = useContext(CustomContext);
    const [friends, setFriends] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchFriends = async () => {
            try {
                const response = await fetch(`http://localhost:8081/api/v1/customer/allFriends?myId=${user?.user?.id}`)
                if (response.ok) {
                    const result = await response.json();
                    setFriends(result);
                } else {
                    const error = await response.json();
                    console.error("Ошибка сети: " + error)
                }
            } catch (error) {
                console.error("Ошибка сервера: " + error);
            }
        };
        fetchFriends();
    }, [user])

    const goToFriendLibrary = (friendId, friendSurname) => {
        navigate(`/friendLibrary/${friendId}/${friendSurname}`);
    }

    const printListFriends = (friend) => {
        return(
            <div onClick={() => goToFriendLibrary(friend?.id, friend?.surname)}>
                <FrindBlock friend={friend} flagAddFriend={false} flagDeleteFriend={true}/>
            </div>
        )
    };

    return (
        <div>
            <Navbar/>
            <div className='page-content'>
                <PageName name={"Друзья"}/>
                {friends?.length > 0 && friends?.map(friend => {
                    return printListFriends(friend);
                })}
                {friends?.length === 0 && (
                    <div>
                        <p>У вас пока что нет друзей</p>
                    </div>
                )}
            </div>
        </div>
    )

}

export default Friends;