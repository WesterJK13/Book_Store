import {useNavigate} from "react-router";
import {formatDateLocal} from "../utils/date-utils";

const CustomerForSeller = ({customer, flagEdit, flagDelete}) => {

    const navigate = useNavigate();

    const editCustomer = () => {
        navigate(`/editCustomer/${customer?.id}`);
    }

    return (
        <div className='review_block'>
            <p className='str'>Пользователь: {customer?.id}</p>
            <p className='str'>Email: {customer?.email}</p>
            <p className='str'>Фамилия: {customer?.surname}</p>
            <p className='str'>Имя: {customer?.name}</p>
            <p className='str'>Отчество: {customer?.patronymic}</p>
            <p className='str'>Дата рождения: {customer?.birthDate && formatDateLocal(customer?.birthDate)}</p>
            <p className='str'>Номер карты: {customer?.cardNumber}</p>
            <p className='str'>Телефон: {customer?.phone}</p>

            {flagEdit && <button className='button' style={{marginRight: "20px"}} onClick={() => {editCustomer()}}>Редактировать</button>}
        </div>
    )
}

export default CustomerForSeller;