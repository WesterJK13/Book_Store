import "./style.css"
import "../../src/style.css"
import {useContext} from "react";
import {CustomContext} from "../utils/Context";
import MenuAdmin from "./MenuAdmin";
import MenuCustomer from "./MenuCustomer";
import MenuSeller from "./MenuSeller";
import MenuUser from "./MenuUser";
import logo from "./../../src/img/logo.png"
import place from "./../../src/img/place.png";
import {Link} from "react-router-dom";

const Navbar = () => {

    const {user, setUser} = useContext(CustomContext)

    const userRole = (role) => {
        if (role === "CUSTOMER") {
            return <MenuCustomer/>
        } else if (role === "SELLER") {
            return <MenuSeller/>
        } else if (role === "ADMIN") {
            return <MenuAdmin/>
        } else {
            return <MenuUser/>
        }
    }

    return (
        <nav className="nav">
            <div className='container nav-container'>

                <a className='logoContainer' href="/">
                    <img src={logo} className="logo"/>
                </a>

                <div className="contentContainer">
                    <div className='info'>
                        <div>
                            <p className='content fonts-roboto-black'>Чтение - вот лучшее учение!</p>
                            <a href="https://yandex.ru/maps/geo/vladimir/53057138/?ll=40.422683%2C56.134849&z=11"
                               className='place fonts-roboto-regular'>
                                <img src={place} className="img-place"/>
                                Владимир, место встречи изменить нельзя!
                            </a>
                        </div>
                        {userRole(user.role)}
                    </div>
                    <div className='menu fonts-roboto-black'>
                        <Link to="/catalog" className='menu-elements'>Каталог</Link>
                        <Link to="/aboutUs" className='menu-elements'>О нас</Link>
                    </div>
                </div>
            </div>
        </nav>
    );
}

export default Navbar;