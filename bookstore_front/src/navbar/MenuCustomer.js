import {useContext} from "react";
import {CustomContext} from "../utils/Context";
import userpng from '../img/user.png'
import arrow from  '../img/arrow.png'

const MenuCustomer = () => {

    const {user, setUser} = useContext(CustomContext);

    const onclickArrow = () => {
        const menu = document.getElementById('menu')
        if (menu.getAttribute('display') === 'yes') {
            menu.classList.add('menu-user')
            menu.setAttribute("display", "none")
        } else {
            menu.classList.remove('menu-user')
            menu.setAttribute("display", "yes")
        }
    }

    const onclickExit = () => {
        localStorage.clear();
    }

    return (
        <span className='user'>
            <div className='user-name' onClick={onclickArrow}>
                <img src={userpng} className='menu-icon-userpng'/>
                <p className='fonts-roboto-black'>{`${user.name} ${user.surname}`}</p>
                <img src={arrow} className='menu-icon-arrow'/>
            </div>
            <div id='menu' display='none' className='menu-user'>
                <a href='/profile' className='menu-link'>
                    <div className='menu-list fonts-roboto-black'>Профиль</div>
                </a>
                <a href='/basket' className='menu-link'>
                    <div className='menu-list fonts-roboto-black'>Корзина</div>
                </a>
                <a href='/customerOrders' className='menu-link'>
                    <div className='menu-list fonts-roboto-black'>Заказы</div>
                </a>
                <a href='/favorites' className='menu-link'>
                    <div className='menu-list fonts-roboto-black'>Избранное</div>
                </a>
                <a href='/library' className='menu-link'>
                    <div className='menu-list fonts-roboto-black'>Библиотека</div>
                </a>
                <a href='/friends' className='menu-link'>
                    <div className='menu-list fonts-roboto-black'>Друзья</div>
                </a>
                <a href='/notFriends' className='menu-link'>
                    <div className='menu-list fonts-roboto-black'>Пользователи</div>
                </a>
                <a href='/friendApplications' className='menu-link'>
                    <div className='menu-list fonts-roboto-black'>Заявки в друзья</div>
                </a>
                <a href='/' onClick={onclickExit} className='menu-link'>
                    <div className='menu-list fonts-roboto-black'>Выход</div>
                </a>
            </div>
        </span>
    )

}

export default MenuCustomer;