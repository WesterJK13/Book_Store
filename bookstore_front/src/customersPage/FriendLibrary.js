import {useParams} from "react-router";
import {useEffect, useState} from "react";
import Navbar from "../navbar/Navbar";
import PageName from "../pages/PageName";
import ProductList from "../pages/products/ProductList";

const FriendLibrary = () => {

    const {friendId, friendSurname} = useParams();
    const [productsInLibrary, setProductsInLibrary] = useState([]);

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await fetch(`http://localhost:8081/api/v1/customer/allProductsInLibrary?myId=${friendId}`);
                if (response.ok) {
                    const result = await response.json();
                    setProductsInLibrary(result);
                } else {
                    console.error("Ошибка сети")
                }
            } catch (error) {
                console.error("Ошибка сети: " + error);
            }
        };
        fetchProducts();
    }, [friendId])

    return (
        <div>
            <Navbar/>
            <div className='page-content'>
                <PageName name={`Библиотека друга: ${friendSurname}`}/>
                {productsInLibrary && productsInLibrary.length > 0 &&
                    <ProductList products={productsInLibrary} flagBasket={false} flagFavourites={false}
                                 flagDeleteFromBasket={false} flagDeleteFromFavourites={false} makeReview={true}/>}
            </div>
        </div>
    )

}

export default FriendLibrary;