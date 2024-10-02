import Navbar from "../navbar/Navbar";
import '../style.css'
import PageName from "./PageName";
import {useContext, useEffect, useState} from "react";
import {CustomContext} from "../utils/Context";

const Profile = () => {

    const user = useContext(CustomContext);
    const [customer, setCustomer] = useState();

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                if (user?.user.id) {
                    const response = await fetch(`http://localhost:8081/api/v1/customer/myProfile?myId=${user.user.id}`)
                    if (response.ok) {
                        const result = await response.json();
                        setCustomer(result);
                    } else {
                        const errorData = await response.json();
                        console.error("Ошибка сервера: " + errorData);
                    }
                }
            } catch (error) {
                console.error(error);
            }
        }
        fetchUserData();
    }, [user])

    return (
        <div>
            <Navbar/>
            <div className='page-content'>
                <PageName name={'Профиль'}/>

                {customer?.id &&
                    <div className='info-block'>
                        <p className='naming fonts-roboto-black'>Имя:</p>
                        <p className='info-str'>{customer.name}</p>
                    </div>
                }
                {customer?.patronymic &&
                    <div className='info-block'>
                        <p className='naming fonts-roboto-black'>Отчество:</p>
                        <p className='info-str'>{customer.patronymic}</p>
                    </div>
                }
                {customer?.surname &&
                    <div className='info-block'>
                        <p className='naming fonts-roboto-black'>Фамилия:</p>
                        <p className='info-str'>{customer.surname}</p>
                    </div>
                }
                {customer?.email &&
                    <div className='info-block'>
                        <p className='naming fonts-roboto-black'>Email:</p>
                        <p className='info-str'>{customer.email}</p>
                    </div>
                }
                {customer?.cardNumber &&
                    <div className='info-block'>
                        <p className='naming fonts-roboto-black'>Номер карты лояльности:</p>
                        <p className='info-str'>{customer.cardNumber}</p>
                    </div>
                }
            </div>
        </div>
    )

}

export default Profile;