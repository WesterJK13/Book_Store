import Navbar from "../navbar/Navbar";
import PageName from "../pages/PageName";
import ProductList from "../pages/products/ProductList";
import {useContext, useState} from "react";
import {CustomContext} from "../utils/Context";
import {useEffect} from "react";
import {useNavigate} from "react-router";

const Basket = () => {

    const user = useContext(CustomContext);
    const [basket, setBasket] = useState();
    const navigate = useNavigate();

    useEffect(() => {
        const fetchFavourites = async () => {
            try{
                const response = await fetch(`http://localhost:8081/api/v1/customer/getProductsFromBasket?customerId=${user?.user?.id}`)
                if (response.ok) {
                    const result = await response.json();
                    setBasket(result);
                } else {
                    const result = await response.json();
                    console.error("Ошибка сервера: " + result);
                }
            } catch (error) {
                console.log("Ошибка сервера: " + error);
            }
        };
        fetchFavourites();
    }, [user])

    const makeOrder = async () => {
        try {
            const queryParams = {
                method: 'POST'
            }
            const response = await fetch(`http://localhost:8081/api/v1/customer/registerOrder?customerId=${user?.user?.id}`, queryParams)
            if (response.ok) {
                const productInBasket = await fetch(`http://localhost:8081/api/v1/customer/getProductsFromBasket?customerId=${user?.user?.id}`)
                if (productInBasket.ok) {
                    const result = await productInBasket.json();
                    setBasket(result);
                } else {
                    const result = await response.json();
                    console.error("Ошибка сервера: " + result);
                }
                alert("Заказ успешно софрмирован, вы можете отследить статус в разделе Заказы.");
            } else {
                const result = await response.json();
                console.error("Ошибка сервера: " + result);
            }
        } catch (error) {
            console.error("Ошибка сервера: " + error);
        }
    }

    const goToCatalog = () => {
        navigate('/catalog');
    }

    return(
        <div>
            <Navbar/>
            <div className='page-content'>
                <PageName name={"Корзина"}/>
                <ProductList products={basket} flagBasket={false} flagFavourites={true} flagDeleteFromBasket={true} flagDeleteFromFavourites={false} makeReview={false}/>
                <div className='pagination_block'>
                    {
                        basket?.length > 0 &&
                        <button onClick={makeOrder} className='button'>
                            Оформить заказ
                        </button>
                    }
                    {
                        basket?.length === 0 &&
                        <div>
                            <p className='info-str' style={{padding: "20px", marginBottom: "20px", fontSize: "25px"}}>
                                Вы пока что не добавляли товары в Корзину. Перейти к каталогу для выбора?
                            </p>
                            <div className='pagination_block'>
                                <button onClick={goToCatalog} className='button'>
                                    Перейти в Каталог
                                </button>
                            </div>
                        </div>
                    }
                </div>
            </div>
        </div>
    )
}

export default Basket;