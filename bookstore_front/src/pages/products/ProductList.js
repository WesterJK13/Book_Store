import Product from "./Product";
import '../../style.css';

const ProductList = ({products, flagBasket, flagFavourites, flagDeleteFromBasket, flagDeleteFromFavourites, makeReview}) => {

    function listProduct(product) {
        return (
            <Product key={product?.id} product={product} flagBasket={flagBasket} flagFavourites={flagFavourites} flagDeleteFromBasket={flagDeleteFromBasket} flagDeleteFromFavourites={flagDeleteFromFavourites} makeReview={makeReview}/>
        )
    }

    return (
        <div className='products'>
            <div className='container_for_products'>
                <div className='list-items '>
                    {products  && products.map((product) => {
                        return listProduct(product);
                    })}
                </div>
            </div>
        </div>
    )


}

export default ProductList;