import {useNavigate, useParams} from "react-router";
import {useContext, useEffect, useState} from "react";
import Navbar from "../../navbar/Navbar";
import PageName from "../PageName";
import "../../style.css"
import "../../button/button.css"
import {CustomContext} from "../../utils/Context";
import {formatDateLocal} from "../../utils/date-utils";

const ProductInfo = () => {

    const user = useContext(CustomContext);
    const {productId, makeReview} = useParams();
    const [product, setProduct] = useState();
    const [reviewByAll, setReviewByAll] = useState(false);
    const [reviewByFriends, setReviewByFriends] = useState(false);
    const [reviews, setReviews] = useState([]);
    const [reviewsByFriends, setReviewsByFriends] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchData = async (id) => {
            try {
                const response = await fetch(`http://localhost:8081/api/v1/general/product?productId=${id}`)
                if (response.ok) {
                    const result = await response.json();
                    setProduct(result);
                } else {
                    const result = response.json();
                    console.error("Ошибка сервера: " + result);
                }
            } catch (error) {
                console.log("Ошибка сервера: " + error)
            }
        };
        const fetchReviews = async (id) => {
            try {
                const response = await fetch(`http://localhost:8081/api/v1/general/reviewForAllByProductId?productId=${id}`)
                if (response.ok) {
                    const result = await response.json();
                    setReviews(result);
                } else {
                    const result = response.json();
                    console.error("Ошибка сервера: " + result);
                }
            } catch (error) {
                console.log("Ошибка сервера: " + error)
            }
        };
        const fetchReviewByFriends = async (customerId, productId) => {
            try {
                const response = await fetch(`http://localhost:8081/api/v1/customer/reviewByFriendsForProduct?productId=${productId}&customerId=${customerId}`)
                if (response.ok) {
                    const result = await response.json();
                    setReviewsByFriends(result);
                } else {
                    const result = response.json();
                    console.error("Ошибка сервера: " + result);
                }
            } catch (error) {
                console.error("Ошибка сети: " + error);
            }
        }

        fetchData(productId);
        fetchReviews(productId);
        fetchReviewByFriends(user?.user?.id, productId);
    }, [user, productId])

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
        if (user?.user?.id === undefined) {
            alert('Необходимо войти в аккаунт')
        } else {
            try {
                const response = await fetch('http://localhost:8081/api/v1/customer/addInBasket?customerId=' + user?.user?.id + '&productId=' + product?.id, requestOptions);
                if (response.ok) {
                    alert('Товар успешно добавлен в корзину');
                } else {
                    const errorData = await response.json();
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
        if (user?.user?.id === undefined) {
            alert('Необходимо войти в аккаунт')
        } else {
            try {
                const response = await fetch('http://localhost:8081/api/v1/customer/addInFavourites?customerId=' + user?.user?.id + '&productId=' + product?.id, requestOptions);
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

    const seeReviewByAll = () => {
        if (reviewByAll === false) {
            setReviewByAll(true);
        } else {
            setReviewByAll(false);
        }
    }

    const seeReviewByFriends = () => {
        if (reviewByFriends === false) {
            setReviewByFriends(true);
        } else {
            setReviewByFriends(false);
        }
    }

    const paintReview = (reviews, allFlag, friendFlag) => {
        if (reviews.length > 0) {
            return (
                <div>
                    {reviews.map((review, index) => {
                        return (<div className='review_block'>
                            <p style={{paddingBottom: "5px"}}><strong>Отзыв {index + 1}</strong></p>
                            <p className='str'>Отзыв от: {review?.customer?.surname} {review?.customer?.name} {review?.customer?.patronymic}</p>
                            {allFlag && <p className='str'>{review?.commentForAll}</p>}
                            {allFlag && <p className='str'>{review?.reviewDateForAll && formatDateLocal(review?.reviewDateForAll)}</p>}
                            {friendFlag && <p className='str'>{review?.commentForFriends}</p>}
                            {friendFlag && <p className='str'>{review?.reviewDateForFriends && formatDateLocal(review?.reviewDateForFriends)}</p>}

                        </div>)
                    })}
                </div>
            )
        } else {
            return (
                <div>
                    <p style={{paddingTop: "10px", fontSize: "20px"}}>На данный момент у товара нет отзывов, но вы
                        можете стать первым, если приобретете данный товар)</p>
                </div>
            )
        }
    }

    function makeReviewForProduct(productId, productName) {
        navigate(`/makeReview/${productId}/${productName}`)
    }

    const checkRoleNotAdminOrSeller = () => {
        return !(user?.user?.role === 'ADMIN' || user?.user?.role === 'SELLER');
    }

    return (
        <div>
            <Navbar/>
            <div className='page-content flex_in_studio'>
                <div className='left_info_block'>
                    <PageName name={'Страница товара: ' + product?.name}/>
                    <div className='product_img'>
                        <img src={product?.link} alt={'Обложка товара'} style={{maxWidth: 'auto', maxHeight: '500px'}}/>
                    </div>
                    <div className='evaluation_block product_img'>
                        <p style={{fontSize: "20px"}}>
                            <br/>
                            <br/>
                            <button className='big_button' onClick={seeReviewByAll}>
                                Смотреть отзывы всех пользователей
                            </button>
                            <br/>
                            <br/>
                            {user?.user?.id &&
                                <button className='big_button' onClick={seeReviewByFriends}>
                                    Смотреть отзывы друзей
                                </button>}
                        </p>
                    </div>
                </div>
                <div className='right_info_block'>
                    <div>
                        <p style={{fontSize: "20px", paddingTop: "70px"}}>
                            <strong>Название: </strong> {product?.name}
                        </p>
                        <p style={{fontSize: "20px", paddingTop: "20px"}}>
                            <strong>Количество товара в наличии: </strong> {product?.quantity}
                        </p>
                        <p style={{fontSize: "20px", paddingTop: "20px"}}>
                            <strong>Цена: </strong> {product?.price} руб.
                        </p>
                        {
                            product?.authors && <p style={{fontSize: "20px", paddingTop: "20px"}}>
                                <strong>Авторы: </strong> {printAuthors(product?.authors)}
                            </p>
                        }
                        <p style={{fontSize: "20px", paddingTop: "20px"}}>
                            <strong>Описание: </strong> {product?.definition}
                        </p>
                        <div style={{paddingTop: "20px"}}>
                            {checkRoleNotAdminOrSeller() && <button className='button' style={{marginRight: "10px"}} onClick={addInBasket}>Добавить в
                                Корзину
                            </button>}
                            {checkRoleNotAdminOrSeller() && <button className='button' style={{marginRight: "10px"}} onClick={addInFavourites}>Добавить
                                в Избранное
                            </button>}
                            {makeReview === "true" &&
                                <button className='button' style={{marginTop: "10px"}} onClick={() => makeReviewForProduct(productId, product?.name)}>
                                    Оставить отзыв
                                </button>
                            }
                        </div>
                    </div>
                    {reviewByAll === true &&
                        <div style={{paddingTop: "70px"}}>
                            <p style={{fontSize: "30px"}}>
                                <strong>Отзывы пользователей</strong>
                            </p>
                            {paintReview(reviews, true, false)}
                        </div>
                    }

                    {reviewByFriends === true &&
                        <div style={{paddingTop: "70px"}}>
                            <p style={{fontSize: "30px"}}>
                                <strong>Отзывы друзей</strong>
                            </p>
                            {paintReview(reviewsByFriends, false, true)}
                        </div>
                    }
                </div>
            </div>
        </div>
    )
}

export default ProductInfo;