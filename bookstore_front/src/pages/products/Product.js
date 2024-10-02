import '../../style.css';
import basketImg from "./../../img/basket.png";
import favouretesImg from "./../../img/favourites.png";
import deletingImg from "./../../img/deleting.png";
import {useContext, useState} from "react";
import {CustomContext} from "../../utils/Context";
import {useNavigate} from "react-router";

const Product = ({product, flagBasket, flagFavourites, flagDeleteFromBasket, flagDeleteFromFavourites, makeReview}) => {

    const {user, setUser} = useContext(CustomContext);
    const navigate = useNavigate();
    const [productId, setProductId] = useState(product?.id);


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

    const addInBasket = async () => {
        const requestOptions = {
            method: 'POST'
        }
        if (user?.id === undefined) {
            alert('Необходимо войти в аккаунт')
        } else {
            try {
                const response = await fetch('http://localhost:8081/api/v1/customer/addInBasket?customerId=' + user?.id + '&productId=' + product?.id, requestOptions);
                if (response.ok) {
                    const result = await response.text();
                    alert(result);
                } else {
                    const errorData = await response.text();
                    console.error("Ошибка сервера: ", errorData);
                }
            } catch (error) {
                console.error("Ошибка сети: " + error);
            }
        }
    }

    const addInFavourites = async () => {
        const requestOptions = {
            method: 'POST'
        }
        if (user?.id === undefined) {
            alert('Необходимо войти в аккаунт')
        } else {
            try {
                const response = await fetch('http://localhost:8081/api/v1/customer/addInFavourites?customerId=' + user?.id + '&productId=' + product?.id, requestOptions);
                if (response.ok) {
                    alert('Товар успешно добавлен в избранное');
                } else {
                    const errorData = await response.json();
                    console.error("Ошибка сервера: ", errorData);
                }
            } catch (error) {
                console.error("Ошибка сети: " + error);
            }
        }
    }

    const deleteFromBasket = async () => {
        const requestOptions = {
            method: 'POST'
        }
        if (user?.id === undefined) {
            alert('Необходимо войти в аккаунт')
        } else {
            try {
                const response = await fetch('http://localhost:8081/api/v1/customer/deleteProductFromBasket?customerId=' + user?.id + '&productId=' + product?.id, requestOptions);
                if (response.ok) {
                    alert('Товар успешно удален из корзины.');
                    window.location.reload();
                } else {
                    const errorData = await response.json();
                    console.error("Ошибка сервера: ", errorData);
                }
            } catch (error) {
                console.error("Ошибка сети: " + error);
            }
        }
    }

    const deleteFromFavourites = async () => {
        const requestOptions = {
            method: 'POST'
        }
        if (user?.id === undefined) {
            alert('Необходимо войти в аккаунт')
        } else {
            try {
                const response = await fetch('http://localhost:8081/api/v1/customer/deleteProductFromFavourites?customerId=' + user?.id + '&productId=' + product?.id, requestOptions);
                if (response.ok) {
                    alert('Товар успешно удален из избранного.');
                    window.location.reload();
                } else {
                    const errorData = await response.json();
                    console.error("Ошибка сервера: ", errorData);
                }
            } catch (error) {
                console.error("Ошибка сети: " + error);
            }
        }
    }

    const checkRoleNotAdminOrSeller = () => {
        return !(user?.role === 'ADMIN' || user?.role === 'SELLER');
    }


    const goToProduct = () => {
        navigate(`/product/${productId}/${makeReview}`);
    }

    return (
        <div className='product' onClick={goToProduct}>
            <div>
                <div className='product_img'>
                    <img src={product?.link} alt={'Обложка товара'} style={{maxWidth: '80%', maxHeight: 'auto'}}/>
                </div>
                <p className='product_info'>
                    <strong>Наименование: </strong>{product?.name}
                </p>
                <p className='product_info'>
                    <strong>Описание: </strong>{product?.definition.substring(0, 300)} ...
                </p>
                <p className='product_info'>
                    <strong>Авторы: </strong>{printAuthors(product?.authors)}
                </p>
                <p className='product_info'>
                    <strong>Цена: </strong>{product?.price}
                </p>

                <div>
                    {checkRoleNotAdminOrSeller() && flagBasket && <button className='icon_product' onClick={(e) => {
                        e.stopPropagation();
                        addInBasket();
                    }}><img className='iconca' src={basketImg}
                            alt={'Иконка Корзины'}/></button>}

                    {checkRoleNotAdminOrSeller() && flagFavourites && <button className='icon_product' onClick={(e) => {
                        e.stopPropagation();
                        addInFavourites();
                    }
                    }><img className='iconca' src={favouretesImg}
                           alt={'Иконка Избранное'}/></button>}

                    {checkRoleNotAdminOrSeller() && flagDeleteFromBasket && <button className='icon_product' onClick={(e) => {
                        e.stopPropagation();
                        deleteFromBasket();
                    }
                    }><img className='iconca' src={deletingImg}
                           alt={'Иконка Удаление из корзины'}/>
                    </button>}

                    {checkRoleNotAdminOrSeller() && flagDeleteFromFavourites && <button className='icon_product' onClick={(e) => {
                        e.stopPropagation();
                        deleteFromFavourites();
                    }
                    }><img className='iconca' src={deletingImg}
                           alt={'Иконка Удаление из корзины'}/>
                    </button>}
                </div>
            </div>
        </div>
    )
}

export default Product;