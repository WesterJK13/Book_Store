import Navbar from "../navbar/Navbar";
import PageName from "../pages/PageName";
import {useContext, useEffect, useState} from "react";
import {CustomContext} from "../utils/Context";
import ProductList from "../pages/products/ProductList";

const Library = () => {

    const user = useContext(CustomContext);
    const [products, setProducts] = useState();


    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await fetch(`http://localhost:8081/api/v1/customer/allProductsInLibrary?myId=${user?.user?.id}`)
                if (response.ok) {
                    const result = await response.json();
                    setProducts(result);
                } else {
                    const error = await response.json();
                    console.error("Ошибка сети: " + error);
                }
            } catch (error) {
                console.error("Ошибка сети: " + error);
            }
        };
        fetchProducts();
    }, [user])

    return (
        <div>
            <Navbar/>
            <div className='page-content'>
                <PageName name={"Библиотека"}/>
                <ProductList products={products} flagBasket={false} flagFavourites={false} flagDeleteFromBasket={false} flagDeleteFromFavourites={false} makeReview={true}/>
            </div>
        </div>
    )
}

export default Library;