import {useNavigate} from "react-router";

const ProductForAdminAndSeller = ({product}) => {

    const navigate = useNavigate();

    const editCustomer = () => {
        navigate(`/editProduct/${product?.id}`);
    }

    function printAvailability(status) {
        if (status) {
            return "Есть в наличии"
        } else {
            return "Товар отсутствует на складе"
        }
    }

    const printAuthors = (authors) => {
        return authors.map((auhtor, index) => {
                if (index === authors.length - 1) {
                    return auhtor.fio;
                } else {
                    return auhtor.fio + ', '
                }
            }
        );
    }

    return (
        <div className='review_block'>
            <p className='str'>Продукт: {product?.id}</p>
            <p className='str'>Наименование: {product?.name}</p>
            <p className='str'>Описание: {product?.definition}</p>
            <p className='str'>Цена: {product?.price}</p>
            <p className='str'>Наличие на складе: {printAvailability(product?.availability)}</p>
            <p className='str'>Количество на складе: {product?.quantity}</p>
            <p className='str'>Ссылка на картинку обложки: {product?.link}</p>
            <p className='str'>Авторы: {printAuthors(product?.authors)}</p>

            <button className='button' style={{marginRight: "20px", marginTop: "10px"}} onClick={() => {editCustomer()}}>Редактировать</button>
        </div>
    )

}

export default ProductForAdminAndSeller;