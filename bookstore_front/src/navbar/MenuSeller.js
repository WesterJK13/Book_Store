import {useContext} from "react";
import {CustomContext} from "../utils/Context";
import userpng from "../img/user.png";
import arrow from "../img/arrow.png";
import './style.css'

const MenuSeller = () => {

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
                <a href='/customers' className='menu-link'>
                    <div className='menu-list fonts-roboto-black'>Пользователи</div>
                </a>
                <a href='/adminProducts' className='menu-link'>
                    <div className='menu-list fonts-roboto-black'>Товары</div>
                </a>
                <a href='/sellerOrders' className='menu-link'>
                    <div className='menu-list fonts-roboto-black'>Заказы для сборки</div>
                </a>
                <a href='/sellerOrdersFinish' className='menu-link'>
                    <div className='menu-list fonts-roboto-black'>Заказы для выдачи</div>
                </a>
                <a href='/' onClick={onclickExit} className='menu-link'>
                    <div className='menu-list fonts-roboto-black'>Выход</div>
                </a>
            </div>
        </span>
    )

}

export default MenuSeller;