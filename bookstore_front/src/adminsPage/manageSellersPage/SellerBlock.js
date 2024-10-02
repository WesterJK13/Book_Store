import {useNavigate} from "react-router";
import {formatDateLocal} from "../../utils/date-utils";

const SellerBlock = ({seller}) => {
    const navigate = useNavigate();

    const editSeller = () => {
        navigate(`/editSeller/${seller?.id}`);
    }

    return (
        <div className='review_block'>
            <p className='str'>Пользователь: {seller?.id}</p>
            <p className='str'>Email: {seller?.email}</p>
            <p className='str'>Фамилия: {seller?.surname}</p>
            <p className='str'>Имя: {seller?.name}</p>
            <p className='str'>Отчество: {seller?.patronymic}</p>
            <p className='str'>Дата рождения: {seller?.birthDate && formatDateLocal(seller?.birthDate)}</p>

            <button className='button' style={{marginRight: "20px"}} onClick={() => {editSeller()}}>Редактировать</button>
        </div>
    )
}

export default SellerBlock