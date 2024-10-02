import Navbar from "../navbar/Navbar";
import PageName from "../pages/PageName";
import {useContext, useEffect, useState} from "react";
import {CustomContext} from "../utils/Context";
import ProductList from "../pages/products/ProductList";

const Favourites = () => {

    const user = useContext(CustomContext);
    const [favourites, setFavourites] = useState();

    useEffect(() => {
        const fetchFavourites = async () => {
            try{
                const response = await fetch(`http://localhost:8081/api/v1/customer/getProductsFromFavourites?customerId=${user?.user?.id}`)
                if (response.ok) {
                    const result = await response.json();
                    setFavourites(result);
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

    return (
        <div>
            <Navbar/>
            <div className='page-content'>
                <PageName name={"Избранное"}/>
                <ProductList products={favourites} flagBasket={true} flagFavourites={false} flagDeleteFromBasket={false} flagDeleteFromFavourites={true} makeReview={false}/>
            </div>
        </div>
    )

}

export default Favourites;