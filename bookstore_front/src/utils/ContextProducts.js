import {createContext, useState} from "react";

export const ProductsContext = createContext()

export const ContextProducts = (props) => {

    const [products, setProducts] = useState();

    const productsValue = {
        products,
        setProducts
    }

    return <ProductsContext.Provider value={productsValue}>
        {props.children}
    </ProductsContext.Provider>

}