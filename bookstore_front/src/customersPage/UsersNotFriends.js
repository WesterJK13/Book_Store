import {useContext, useEffect, useState} from "react";
import {CustomContext} from "../utils/Context";
import Navbar from "../navbar/Navbar";
import PageName from "../pages/PageName";
import FrindBlock from "../pages/friends/FrindBlock";

const UsersNotFriends = () => {

    const user = useContext(CustomContext);
    const [users, setUsers] = useState([]);
    const [surname, setSurname] = useState("");
    const [filteredUsers, setFilteredUsers] = useState([]);

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await fetch(`http://localhost:8081/api/v1/customer/allNotFriends?myId=${user?.user?.id}`)
                if (response.ok) {
                    const result = await response.json();
                    setUsers(result);
                } else {
                    const error = await response.json();
                    console.error("Ошибка сети: " + error);
                }
            } catch (error) {
                console.error("Ошибка сервера: " + error);
            }
        };
        fetchUsers();
    }, [user])


    const printListFriends = (friend) => {
        return(
            <FrindBlock friend={friend} flagAddFriend={true} flagDeleteFriend={false}/>
        )
    };

    const handleSearch = () => {
        if (surname === "") {
            setFilteredUsers(users); // Если поле поиска пустое, показываем всех пользователей
        } else {
            console.log(users)
            const filtered = users.filter(user =>
                user?.surname.toLowerCase().includes(surname.toLowerCase())
            );
            setFilteredUsers(filtered);
        }
    };

    return (
        <div>
            <Navbar/>
            <div className='page-content'>
                <PageName name={"Пользователи системы"}/>

                <div className='width_search'>
                    <input
                        type="text"
                        value={surname}
                        onChange={(e) => setSurname(e.target.value)}
                        className='input_search_string'
                        placeholder="Введите фамилию..."
                    />
                    <button onClick={handleSearch} className='button_search'>
                        Поиск
                    </button>
                </div>

                {surname === "" && users?.length > 0 && users?.map(user => {
                    return printListFriends(user);
                })}
                {surname !== "" && filteredUsers.length > 0 && filteredUsers.map(user => {
                    return printListFriends(user);
                })}
            </div>
        </div>
    )

}

export default UsersNotFriends