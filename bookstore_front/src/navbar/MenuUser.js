import "./style.css"
import "../../src/style.css"
import "../../src/fonts/roboto/fonts.css"

const MenuUser = () => {
    return (
        <span className='user'>
            <a href="/login" className="fonts-roboto-black menu-elements">Вход</a>
        </span>
    )
}

export default MenuUser;